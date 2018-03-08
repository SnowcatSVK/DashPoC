package com.globallogic.dashpoc.services

import com.globallogic.dashpoc.dashboard.DashboardActivity
import com.google.android.apps.auto.sdk.CarActivity
import com.google.android.apps.auto.sdk.CarActivityService

/**
 * Created by snowcat on 8.3.2018.
 */
class CarService: CarActivityService() {
    override fun getCarActivity(): Class<out CarActivity> {
        return DashboardActivity::class.java
    }
}