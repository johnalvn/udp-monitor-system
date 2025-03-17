package co.jedal.test.processor.dto

data class SensorMsgBrokerDTO(
    val msgId: String,
    val source: String,
    val value: String,
    val dateTime: String,
)

