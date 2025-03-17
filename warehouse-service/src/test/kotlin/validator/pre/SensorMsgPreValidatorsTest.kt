package validator.pre

import co.jedal.test.monitor.sensor.dto.UdpMessage
import co.jedal.test.validator.pre.SensorMsgPreValidators
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class SensorMsgPreValidatorsTest {
 private lateinit var validator: SensorMsgPreValidators
 private val standardOut = System.out
 private val outputStreamCaptor = ByteArrayOutputStream()

 @BeforeEach
 fun setUp() {
  validator = SensorMsgPreValidators()
  System.setOut(PrintStream(outputStreamCaptor))
 }


 // Clean up after each test to restore System.out
 @org.junit.jupiter.api.AfterEach
 fun tearDown() {
  System.setOut(standardOut)
 }


}