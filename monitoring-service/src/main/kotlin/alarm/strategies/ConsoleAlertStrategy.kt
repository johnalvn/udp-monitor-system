package co.jedal.test.alarm.strategies

class ConsoleAlertStrategy : AlertStrategy {
    override fun send(message: String) {
        println("[ALERT] $message")
    }
}