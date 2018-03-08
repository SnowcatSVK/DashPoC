package com.globallogic.dashpoc.broadcasts

import android.app.UiModeManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import com.globallogic.dashpoc.services.CarStatsService

/**
 * Created by snowcat on 8.3.2018.
 */
class CarModeBroadcastReceiver : BroadcastReceiver() {

    private val SERVICES = arrayOf<Class<*>>(CarStatsService::class.java)
    private val TAG = "CarModeBR"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "intent: " + intent)
        val action = intent.action
        if (UiModeManager.ACTION_ENTER_CAR_MODE == action) {
            startServices(context)
        } else if (UiModeManager.ACTION_EXIT_CAR_MODE == action) {
            stopServices(context)
        } else if (Intent.ACTION_MY_PACKAGE_REPLACED == action) {
            val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_CAR) {
                startServices(context)
            }
        } else {
            throw AssertionError()
        }
    }

    private fun startServices(context: Context) {
        Log.d(TAG, "starting services")
        for (cls in SERVICES) {
            context.startService(Intent(context, cls))
        }
    }

    private fun stopServices(context: Context) {
        Log.d(TAG, "stopping services")
        for (cls in SERVICES) {
            context.stopService(Intent(context, cls))
        }
    }
}