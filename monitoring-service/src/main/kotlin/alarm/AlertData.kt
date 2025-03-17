package co.jedal.test.alarm

data class AlertData(
    val message: String,
    val severity: AlertSeverity,
    val timestamp: Long = System.currentTimeMillis()
)