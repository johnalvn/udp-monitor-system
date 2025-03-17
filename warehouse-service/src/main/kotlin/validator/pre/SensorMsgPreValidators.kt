package co.jedal.test.validator.pre

import co.jedal.test.validator.CompositeValidator
import co.jedal.test.validator.LengthValidator
import co.jedal.test.validator.RegexValidator
import co.jedal.test.validator.StringValidator

class SensorMsgPreValidators {

    fun execute(message: String): Boolean{
        val validators = listOf(
            RegexValidator(
                """sensor_id=[a-zA-Z][0-9]{1,3};\s*value=\d{1,4}""",
                "Invalid sensor data format"
            ),
            LengthValidator(
                10, 50,
                "Sensor data string must be between 10 and 50 characters"
            ),
            // Custom validator for value range
            object : StringValidator {
                override val errorMessage = "Sensor value must be between 0 and 100"
                override fun validate(input: String): Boolean {
                    val valueMatch = Regex("""value=(\d+)""").find(input)
                    val value = valueMatch?.groupValues?.get(1)?.toIntOrNull() ?: return false
                    return value in 0..100
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