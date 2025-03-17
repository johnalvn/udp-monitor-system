package co.jedal.test.processor.serializer

import co.jedal.test.processor.dto.SensorMsgBrokerDTO

class SensorMsgExtractor {

    fun extractSensorData(input: String): Map<String, String> {
        val result = mutableMapOf<String, String>()

        // Extract sensor_id using regex
        val sensorIdPattern = """sensor_id=([a-zA-Z][0-9]{1,3})""".toRegex()
        sensorIdPattern.find(input)?.let { matchResult ->
            result["sensor_id"] = matchResult.groupValues[1]
        }

        // Extract value using regex
        val valuePattern = """value=(\d+)""".toRegex()
        valuePattern.find(input)?.let { matchResult ->
            result["value"] = matchResult.groupValues[1]
        }

        return result
    }

}