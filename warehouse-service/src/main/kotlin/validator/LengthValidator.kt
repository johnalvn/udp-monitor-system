package co.jedal.test.validator

import co.jedal.test.monitor.sensor.dto.UdpMessage

class LengthValidator(
    private val minLength: Int,
    private val maxLength: Int,
    override val errorMessage: String
) : UdpMessageValidator {
    override fun validate(input: UdpMessage): Boolean {
        return input.content.trim().length in minLength..maxLength
    }
}