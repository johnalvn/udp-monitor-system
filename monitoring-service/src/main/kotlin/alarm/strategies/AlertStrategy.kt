package co.jedal.test.alarm.strategies

interface AlertStrategy {
    fun send(message: String)
}
