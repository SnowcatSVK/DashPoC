package com.globallogic.dashpoc.logger

import android.os.Environment
import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import java.io.*
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.GZIPOutputStream

/**
 * Created by snowcat on 8.3.2018.
 */
class StatsLogger(private val mHandler: Handler) {

    private val TAG = "StatsLogger"

    private val LOG_FILE_NAME_FORMAT = SimpleDateFormat("dd-MM-YYYY_HH-mm-ss", Locale.US)

    private val JSON_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.US)

    private val AUTO_SYNC_TIMEOUT_MS = 30000

    private var mLogWriter: Writer? = null
    private var mLogFile: File = File("temp")
    private var mGson = Gson()

    private val mListeners = ArrayList<Listener>()

    interface Listener {
        fun onLogFileComplete(logFile: File)
    }

    fun writeMeasurements(date: Date, values: Map<String, Any>, prefix: String) {
        try {
            createLogStream(prefix)
            mLogWriter?.let {
                val o = HashMap<String, Any>()
                o["timestamp"] = JSON_DATE_FORMAT.format(date)
                values.forEach { (mKey, value) ->
                    val key = mKey.replace(Regex("[^a-zA-Z0-9]"), "_")
                    o[key] = value
                }
                it.write(mGson.toJson(o))
                it.write("\n")
                scheduleSyncTimeout()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error saving measurements", e)
            close()
        }
    }

    @Synchronized
    @Throws(IOException::class)
    private fun createLogStream(prefix: String) {
        if (mLogWriter != null) {
            return
        }
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            return
        }

        val formattedDate = LOG_FILE_NAME_FORMAT.format(Date())
        mLogFile = File(getLogsDir(), "$prefix-$formattedDate.log.gz")
        mLogWriter = OutputStreamWriter(GZIPOutputStream(FileOutputStream(mLogFile)),
                StandardCharsets.UTF_8)
        Log.i(TAG, "Started log file: " + mLogFile.absolutePath)
    }

    @Throws(IOException::class)
    private fun getLogsDir(): File {
        val logsDir = File(Environment.getExternalStorageDirectory(), "DashPoC")
        if (!logsDir.exists()) {
            if (!logsDir.mkdirs()) {
                Log.e(TAG, "Failed to create CarLogs folder: " + logsDir.absolutePath)
                throw IOException("Failed to create CarLogs folder")
            }
        }
        return logsDir
    }

    private val mSync = Runnable {
        Log.d(TAG, "Auto-sync")
        close()
    }

    private fun scheduleSyncTimeout() {
        mHandler.removeCallbacks(mSync)
        mHandler.postDelayed(mSync, AUTO_SYNC_TIMEOUT_MS.toLong())
    }

    @Synchronized
    fun close() {
        mLogWriter?.let {
            try {
                it.flush()
                it.close()
            } catch (e: Exception) {
                Log.e(TAG, "Error closing log stream", e)
            }

            for (listener in mListeners) {
                try {
                    listener.onLogFileComplete(mLogFile)
                } catch (e: Exception) {
                    Log.e(TAG, "Error from listener", e)
                }

            }
        }
        mLogWriter = null
        mHandler.removeCallbacks(mSync)
    }

    fun registerListener(listener: Listener) {
        mListeners.add(listener)
    }
}