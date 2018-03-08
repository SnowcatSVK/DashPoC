package com.globallogic.dashpoc.db

/**
 * Created by snowcat on 8.3.2018.
 */
object Projection {
    val PRIMARY_CONSUMPTION_LONG = "longTermConsumptionPrimary"
    val SECONDARY_CONSUMPTION_LONG = "longTermConsumptionSecondary"
    val PRIMARY_CONSUMPTION_SHORT = "shortTermConsumptionPrimary"
    val SECONDARY_CONSUMPTION_SHORT = "shortTermConsumptionSecondary"
    val PRIMARY_CONSUMPTION_CYCLE = "cycleConsumptionPrimary"
    val SECONDARY_CONSUMPTION_CYCLE = "cycleConsumptionSecondary"
    val PRIMARY_TANK_LEVEL = "tankLevelPrimary"
    val SECONDARY_TANK_LEVEL = "tankLevelSecondary"

    val values = listOf(
            PRIMARY_CONSUMPTION_LONG,
            SECONDARY_CONSUMPTION_LONG,
            PRIMARY_CONSUMPTION_SHORT,
            SECONDARY_CONSUMPTION_SHORT,
            PRIMARY_CONSUMPTION_CYCLE,
            SECONDARY_CONSUMPTION_CYCLE,
            PRIMARY_TANK_LEVEL,
            SECONDARY_TANK_LEVEL)
}