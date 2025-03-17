package co.jedal.test.integration.handler

import co.jedal.test.integration.handler.dto.SensorMsgBrokerDTO
import co.jedal.test.integration.handler.sensor.SensorHandler
import co.jedal.test.integration.handler.sensor.SensorTypeEnum

class AlertDispatcher {
    fun dispatch(sensor: SensorHandler) {
        sensor.handle()
    }

    fun getSensorType(message: SensorMsgBrokerDTO): SensorTypeEnum {
        return when (message.source[0].lowercase()) {
            't'.toString() -> SensorTypeEnum.TEMPERATURE
            'h'.toString() -> SensorTypeEnum.HUMIDITY
            else -> SensorTypeEnum.NO_IDENTIFIER
        }
    }

}