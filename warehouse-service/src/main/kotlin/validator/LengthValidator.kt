package co.jedal.test.validator

class LengthValidator(
    private val minLength: Int,
    private val maxLength: Int,
    override val errorMessage: String
) : StringValidator {
    override fun validate(input: String): Boolean {
        return input.length in minLength..maxLength
    }
}