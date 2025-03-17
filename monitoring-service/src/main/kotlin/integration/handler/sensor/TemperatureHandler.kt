package co.jedal.test.integration.handler.sensor

import co.jedal.test.config.ConfigManager
import co.jedal.test.integration.handler.dto.SensorMsgBrokerDTO

class TemperatureHandler(private val message: SensorMsgBrokerDTO) : AbstractSensorHandler() {

    override fun handle() {
        message.let {
            println(" [x] Received '$it'")
            val threshold = ConfigManager.get("sensor.temperature.threshold").toString()

            if (it.value.toInt() >threshold.toInt()){
                val alertMsgTemplate = ConfigManager.get("sensor.temperature.alertCriticalMsg").toString()
                val msg = String.format(alertMsgTemplate, it.source, threshold, it.value )

                alertService.sendAlert(alertDataFactory.createCriticalAlert(msg))
            }
        }
    }

}