package co.jedal.test.validator

class RegexValidator(
    private val pattern: Regex,
    override val errorMessage: String
) : StringValidator {
    constructor(pattern: String, errorMessage: String) : this(pattern.toRegex(), errorMessage)

    override fun validate(input: String): Boolean {
        return pattern.matches(input)
    }
}