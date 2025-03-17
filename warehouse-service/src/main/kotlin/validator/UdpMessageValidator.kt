package co.jedal.test.validator

import co.jedal.test.monitor.sensor.dto.UdpMessage

interface UdpMessageValidator {
    fun validate(input: UdpMessage): Boolean
    val errorMessage: String
}