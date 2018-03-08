package com.globallogic.dashpoc.db.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by snowcat on 8.3.2018.
 */
open class ValueRealm(
        @PrimaryKey open var key:String = "",
        open var value: Float = -1f
) : RealmObject()