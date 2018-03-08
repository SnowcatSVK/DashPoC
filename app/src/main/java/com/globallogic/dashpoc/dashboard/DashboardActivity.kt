package com.globallogic.dashpoc.dashboard

import android.os.Bundle
import com.globallogic.dashpoc.R
import com.google.android.apps.auto.sdk.CarActivity
import com.google.android.apps.auto.sdk.DayNightStyle
import com.google.android.apps.auto.sdk.StatusBarController

class DashboardActivity : CarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            val fragment = DashboardFragment()
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()

            setupStatusBar(carUiController.statusBarController)
        }
    }

    private fun setupStatusBar(sc: StatusBarController) {
        sc.setDayNightStyle(DayNightStyle.FORCE_NIGHT)
        sc.showAppHeader()
        sc.showBatteryLevel()
        sc.showClock()
        sc.showConnectivityLevel()
        sc.showMicButton()
        sc.setTitle(applicationContext.getString(R.string.dashboard_title))
    }

}
