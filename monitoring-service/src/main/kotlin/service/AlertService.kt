package co.jedal.test.service

import co.jedal.test.alarm.AlertData
import co.jedal.test.alarm.strategies.AlertStrategy

class AlertService(private var alertStrategy: AlertStrategy) {


    fun setAlertStrategy(alertStrategy: AlertStrategy) {
        this.alertStrategy = alertStrategy
    }

    fun sendAlert(alert: AlertData) {
        println("Sending alert")
        val formattedMessage = formatAlertMessage(alert)
        alertStrategy.send(formattedMessage)
    }

    private fun formatAlertMessage(alert: AlertData): String {
        return "[${alert.severity}] ${alert.message} (${formatTimestamp(alert.timestamp)})"
    }

    private fun formatTimestamp(timestamp: Long): String {

        return java.time.Instant.ofEpochMilli(timestamp).toString()
    }

}