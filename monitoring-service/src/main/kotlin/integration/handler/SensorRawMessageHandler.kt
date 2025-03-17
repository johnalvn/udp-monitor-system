package co.jedal.test.integration.handler

import co.jedal.test.config.ConfigManager
import co.jedal.test.integration.handler.dto.SensorMsgBrokerDTO
import co.jedal.test.integration.handler.sensor.HumidityHandler
import co.jedal.test.integration.handler.sensor.SensorTypeEnum
import co.jedal.test.integration.handler.sensor.TemperatureHandler
import com.google.gson.Gson

class SensorRawMessageHandler {

    private val alertDispatcher: AlertDispatcher = AlertDispatcher()

    fun handler(rawMessage: String) {

        val gson = Gson()
        val sensorMsgBrokerDTO = gson.fromJson(rawMessage, SensorMsgBrokerDTO::class.java)

        when(alertDispatcher.getSensorType(sensorMsgBrokerDTO)){
            SensorTypeEnum.TEMPERATURE -> {
                val sensorHandler = TemperatureHandler(sensorMsgBrokerDTO)
                alertDispatcher.dispatch(sensorHandler)
            }
            SensorTypeEnum.HUMIDITY -> {
                val sensorHandler = HumidityHandler(sensorMsgBrokerDTO)
                alertDispatcher.dispatch(sensorHandler)
            }

            SensorTypeEnum.NO_IDENTIFIER -> {
                val msgTemplate = ConfigManager.get("sensor.msgTemplate.noIdentifiableSensor").toString()
                val msg = String.format(msgTemplate, sensorMsgBrokerDTO.source)
                println(msg)
            }
        }


    }
}