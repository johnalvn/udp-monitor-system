package co.jedal.test.processor.serializer

import co.jedal.test.processor.dto.SensorMsgBrokerDTO
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SensorMsgParser {
    @OptIn(ExperimentalUuidApi::class)
    fun parse(message : String) : SensorMsgBrokerDTO {
        val extractedData = SensorMsgExtractor().extractSensorData(message)


        return extractedData.let {
            SensorMsgBrokerDTO(
                Uuid.random().toString(),
                it["sensor_id"]!!,
                it["value"]!!,
                LocalDateTime.now().toString()
            )
        }
    }
}