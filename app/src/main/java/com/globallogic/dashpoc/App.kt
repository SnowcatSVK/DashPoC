package com.globallogic.dashpoc

import android.app.Application
import io.realm.Realm

/**
 * Created by snowcat on 8.3.2018.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
    }
}