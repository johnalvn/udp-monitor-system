package co.jedal.test.integration.handler.sensor

import co.jedal.test.alarm.AlertDataFactory
import co.jedal.test.alarm.strategies.ConsoleAlertStrategy
import co.jedal.test.service.AlertService

abstract class AbstractSensorHandler: SensorHandler {

    val alertService: AlertService by lazy {
        val consoleAlertStrategy = ConsoleAlertStrategy()
        AlertService(consoleAlertStrategy)
    }

    val alertDataFactory: AlertDataFactory by lazy {
        AlertDataFactory()
    }
}