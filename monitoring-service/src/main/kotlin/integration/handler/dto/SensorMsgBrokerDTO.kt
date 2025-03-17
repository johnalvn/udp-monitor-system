package co.jedal.test.integration.handler.dto

data class SensorMsgBrokerDTO(
    val msgId: String,
    val source: String,
    val value: String,
    val dateTime: String,
)