package co.jedal.test.validator

import co.jedal.test.monitor.sensor.dto.UdpMessage

class CompositeValidator(private val validators: List<UdpMessageValidator>) {

    fun validate(input: UdpMessage): ValidationResult {
        val errors = mutableListOf<String>()

        for (validator in validators) {
            if (!validator.validate(input)) {
                errors.add(validator.errorMessage)
            }
        }

        return ValidationResult(errors.isEmpty(), errors)
    }

}

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
)