package com.globallogic.dashpoc.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.github.martoreto.aauto.vex.CarStatsClient
import com.globallogic.dashpoc.db.DbProvider
import com.globallogic.dashpoc.db.Projection
import com.globallogic.dashpoc.logger.StatsLogger
import io.realm.Realm
import java.util.*

/**
 * Created by snowcat on 8.3.2018.
 */
class CarStatsService : Service() {

    inner class CarStatsBinder : Binder() {
        val statsClient: CarStatsClient
            get() = mStatsClient
    }

    private val mBinder = CarStatsBinder()

    private var mStatsClient: CarStatsClient = CarStatsClient(this)

    private val TAG = "CarStatsService"

    private val dbProvider = DbProvider(Realm.getDefaultInstance())


    private val handler = Handler()

    private lateinit var logger: StatsLogger

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service starting.")
        //MOCK
//        val values = mutableMapOf<String, Any>()
//        values.put(Projection.PRIMARY_CONSUMPTION_LONG, 3.9f)
//        values.put(Projection.PRIMARY_CONSUMPTION_SHORT, 4.1f)
//        values.put(Projection.PRIMARY_CONSUMPTION_CYCLE, 4.2f)
//        values.put(Projection.SECONDARY_CONSUMPTION_LONG, 5.3f)
//        values.put(Projection.SECONDARY_CONSUMPTION_SHORT, 6.2f)
//        values.put(Projection.SECONDARY_CONSUMPTION_CYCLE, 5.5f)
//
//        values.put(Projection.SECONDARY_TANK_LEVEL, 0.4f)
//        values.put(Projection.PRIMARY_TANK_LEVEL, 0.8f)
//
//
//        handler.postDelayed({mCarStatsListener.onNewMeasurements("", null, values)}, 5000)
        mStatsClient.registerListener(mCarStatsListener)
        mStatsClient.start()
        logger = StatsLogger(Handler())
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    private val mCarStatsListener = CarStatsClient.Listener { _, date, values ->
        handler.post {
            insertToDB(values, date)
        }
        logger.writeMeasurements(date, values, "car")
    }

    private fun insertToDB(values: MutableMap<String, Any>, date: Date) {
        val dataBaseValues = HashMap<String, Any>()
        Projection.values.forEach { projectionKey ->
            if (values[projectionKey] != null) {
                val value = values[projectionKey]
                val key = projectionKey.replace(Regex("[^a-zA-Z0-9]"), "_")
                dbProvider.saveValue(key, value as Float).subscribe({
                    Log.d(TAG, "values are inserted to DB")
                    dataBaseValues[key] = value
                }, {
                    Log.e(TAG, "values aren't inserted to DB, ERROR")
                })
            }
        }
        logger.writeMeasurements(date, dataBaseValues, "database")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "Service stopping.")
        mStatsClient.unregisterListener(mCarStatsListener)
        mStatsClient.stop()
        logger.close()
        super.onDestroy()
    }
}