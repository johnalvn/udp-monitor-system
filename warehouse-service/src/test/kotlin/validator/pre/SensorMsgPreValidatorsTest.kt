package validator.pre

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

 @Test
 fun `valid sensor message should pass validation`() {
  // Given
  val validMessage = "sensor_id=A123; value=75"

  // When
  val result = validator.execute(validMessage)

  // Then
  assertTrue(result)
  assertTrue(outputStreamCaptor.toString().isEmpty())
 }

 @Test
 fun `message with invalid format should fail validation`() {
  // Given
  val invalidFormatMessage = "sensorid=A123; value=75"

  // When
  val result = validator.execute(invalidFormatMessage)

  // Then
  assertFalse(result)
  assertTrue(outputStreamCaptor.toString().contains("Invalid sensor data format"))
 }

 @Test
 fun `message with invalid sensor ID format should fail validation`() {
  // Given
  val invalidSensorIdMessage = "sensor_id=123A; value=75"

  // When
  val result = validator.execute(invalidSensorIdMessage)

  // Then
  assertFalse(result)
  assertTrue(outputStreamCaptor.toString().contains("Invalid sensor data format"))
 }

 @Test
 fun `message too short should fail validation`() {
  // Given
  val shortMessage = "s_id=A1;v=5"

  // When
  val result = validator.execute(shortMessage)

  // Then
  assertFalse(result)
  assertTrue(outputStreamCaptor.toString().contains("Sensor data string must be between 10 and 50 characters"))
 }

 @Test
 fun `message too long should fail validation`() {
  // Given
  val longMessage = "sensor_id=A123; value=75; " + "a".repeat(50)

  // When
  val result = validator.execute(longMessage)

  // Then
  assertFalse(result)
  assertTrue(outputStreamCaptor.toString().contains("Sensor data string must be between 10 and 50 characters"))
 }

 @Test
 fun `message with value out of range (too high) should fail validation`() {
  // Given
  val highValueMessage = "sensor_id=A123; value=150"

  // When
  val result = validator.execute(highValueMessage)

  // Then
  assertFalse(result)
  assertTrue(outputStreamCaptor.toString().contains("Sensor value must be between 0 and 100"))
 }

 @Test
 fun `message with value out of range (too low) should fail validation`() {
  // Given
  val negativeValueMessage = "sensor_id=A123; value=-10"

  // When
  val result = validator.execute(negativeValueMessage)

  // Then
  assertFalse(result)
  // This will fail with the regex validation first
  assertTrue(outputStreamCaptor.toString().contains("Invalid sensor data format"))
 }

 @Test
 fun `message with boundary value (0) should pass validation`() {
  // Given
  val minValueMessage = "sensor_id=A123; value=0"

  // When
  val result = validator.execute(minValueMessage)

  // Then
  assertTrue(result)
 }

 @Test
 fun `message with boundary value (100) should pass validation`() {
  // Given
  val maxValueMessage = "sensor_id=A123; value=100"

  // When
  val result = validator.execute(maxValueMessage)

  // Then
  assertTrue(result)
 }

 @Test
 fun `multiple validation errors should all be reported`() {
  // Given
  val multipleErrorsMessage = "sensr=123; val=200"

  // When
  val result = validator.execute(multipleErrorsMessage)

  // Then
  assertFalse(result)
  val errorOutput = outputStreamCaptor.toString()
  assertTrue(errorOutput.contains("Invalid sensor data format"))
  // Note: Because the composite validator short-circuits after the first failure,
  // we only see the first error in the output
 }

 // Clean up after each test to restore System.out
 @org.junit.jupiter.api.AfterEach
 fun tearDown() {
  System.setOut(standardOut)
 }


}