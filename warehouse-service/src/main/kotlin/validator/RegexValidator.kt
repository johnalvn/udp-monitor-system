package co.jedal.test.validator

import co.jedal.test.monitor.sensor.dto.UdpMessage

class RegexValidator(
    private val pattern: Regex,
    override val errorMessage: String
) : UdpMessageValidator {
    constructor(pattern: String, errorMessage: String) : this(pattern.toRegex(), errorMessage)

    override fun validate(input: UdpMessage): Boolean {
        return pattern.matches(input.content.trim())
    }
}