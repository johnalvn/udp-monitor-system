package co.jedal.test.validator

interface StringValidator {
    fun validate(input: String): Boolean
    val errorMessage: String
}