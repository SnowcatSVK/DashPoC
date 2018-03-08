package com.globallogic.dashpoc.dashboard

/**
 * Created by snowcat on 8.3.2018.
 */
enum class DashItemType {
    TYPE_CONSUMPTION;

    companion object {
        fun byNumber(number: Int): DashItemType {
            return TYPE_CONSUMPTION
        }
    }
}