package co.jedal.test.alarm

class AlertDataFactory {
    fun createInfoAlert(message: String): AlertData {
        return AlertData(message, AlertSeverity.INFO)
    }

    fun createWarningAlert(message: String): AlertData {
        return AlertData(message, AlertSeverity.WARNING)
    }

    fun createErrorAlert(message: String): AlertData {
        return AlertData(message, AlertSeverity.ERROR)
    }

    fun createCriticalAlert(message: String): AlertData {
        return AlertData(message, AlertSeverity.CRITICAL)
    }
}