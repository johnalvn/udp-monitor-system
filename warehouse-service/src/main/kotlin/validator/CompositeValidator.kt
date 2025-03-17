package co.jedal.test.validator

class CompositeValidator(private val validators: List<StringValidator>) {

    fun validate(input: String): ValidationResult {
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