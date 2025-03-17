package co.jedal.test.processor

import co.jedal.test.integration.rabbitmq.RabbitmqProducer
import co.jedal.test.processor.dto.SensorMsgBrokerDTO
import co.jedal.test.processor.serializer.SensorMsgExtractor
import co.jedal.test.processor.serializer.SensorMsgParser
import com.google.gson.Gson

class SensorMessageProcessor: Processor {
    override fun process(message: String) {

        val sensorMsgExtractor = SensorMsgParser().parse(message)

        val gson = Gson()

        RabbitmqProducer().sendData(gson.toJson(sensorMsgExtractor))

    }
}