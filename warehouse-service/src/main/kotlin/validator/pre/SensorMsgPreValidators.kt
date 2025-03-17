package co.jedal.test.validator.pre

import co.jedal.test.monitor.sensor.dto.UdpMessage
import co.jedal.test.validator.CompositeValidator
import co.jedal.test.validator.LengthValidator
import co.jedal.test.validator.RegexValidator
import co.jedal.test.validator.UdpMessageValidator
import com.rabbitmq.client.AMQP.Connection.Tune

class SensorMsgPreValidators {

    fun execute(message: UdpMessage): Boolean{



        val validators = listOf(
            RegexValidator(
                """sensor_id=[a-zA-Z][0-9]{1,3};\s*value=\d{1,4}""",
                "Invalid sensor data format"
            ),
            LengthValidator(
                10, 50,
                "Sensor data string must be between 10 and 50 characters"
            ),

            object : UdpMessageValidator {
                override val errorMessage = "Invalid data from sensor, check the port number ${message.listenerPort}"
                override fun validate(input: UdpMessage): Boolean {

                    val values = message.content.split("; ")
                        .associate {
                            val (key, value) = it.split("=")
                            key.trim() to value.trim()
                        }

                    if (input.listenerPort == 3344){
                        if (values["sensor_id"]!!.lowercase()[0] == 't' ){
                            return true
                        }

                    }

                    if (input.listenerPort == 3355){
                        if (values["sensor_id"]!!.lowercase()[0] == 'h' ){
                            return true
                        }
                    }
                    return false

                }
            },
            // Custom validator for value range
            object : UdpMessageValidator {
                override val errorMessage = "Sensor value must be between 0 and 100"
                override fun validate(input: UdpMessage): Boolean {
                    val valueMatch = Regex("""value=(\d+)""").find(input.content)
                    val valueData = valueMatch?.groupValues?.get(1)?.toIntOrNull() ?: return false
                    return valueData in 0..100
                }
            }

        )

        val compositeValidator = CompositeValidator(validators)

        val result = compositeValidator.validate(message)

        if (result.isValid){
            return true
        }
        println("Errors: ${result.errors.joinToString(", ")}")
        return false

    }
}