package co.jedal.test.integration.handler.sensor

import co.jedal.test.config.ConfigManager
import co.jedal.test.integration.handler.dto.SensorMsgBrokerDTO

class HumidityHandler(private val message: SensorMsgBrokerDTO) : AbstractSensorHandler() {
    override fun handle() {
        message.let {
            println(" [x] Received '$it'")

            val humiditySensorThreshold = ConfigManager.get("sensor.humidity.threshold").toString()

            if (it.value.toInt() > humiditySensorThreshold.toInt()){
                val alertMsgTemplate = ConfigManager.get("sensor.humidity.alertCriticalMsg").toString()
                val msg = String.format(alertMsgTemplate, it.source, humiditySensorThreshold, it.value )


                alertService.sendAlert(alertDataFactory.createCriticalAlert(msg))
            }
        }
    }
}