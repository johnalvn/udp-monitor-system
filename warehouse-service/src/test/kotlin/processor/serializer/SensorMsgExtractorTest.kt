package processor.serializer

import co.jedal.test.processor.serializer.SensorMsgExtractor


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SensorMsgExtractorTest {

 private lateinit var extractor: SensorMsgExtractor

 @BeforeEach
 fun setUp() {
  extractor = SensorMsgExtractor()
 }

 @Test
 fun `should extract valid sensor message`() {
  // Given
  val input = "sensor_id=r1; value=90"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertEquals("r1", result["sensor_id"])
  assertEquals("90", result["value"])
  assertEquals(2, result.size)
 }

 @Test
 fun `should extract valid sensor message with spaces`() {
  // Given
  val input = "  sensor_id=r45;   value=123  "

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertEquals("r45", result["sensor_id"])
  assertEquals("123", result["value"])
 }

 @Test
 fun `should extract valid sensor message with different order`() {
  // Given
  val input = "value=90; sensor_id=a123"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertEquals("a123", result["sensor_id"])
  assertEquals("90", result["value"])
 }

 @Test
 fun `should extract only valid sensor_id pattern`() {
  // Given
  val input = "sensor_id=a123; something_else=xyz; value=90"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertEquals("a123", result["sensor_id"])
  assertEquals("90", result["value"])
  assertEquals(2, result.size)  // Should only extract the two valid fields
 }

 @Test
 fun `should return empty map for invalid input`() {
  // Given
  val input = "invalid_format"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertTrue(result.isEmpty())
 }

 @Test
 fun `should extract sensor_id with 1 digit`() {
  // Given
  val input = "sensor_id=a1; value=90"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertEquals("a1", result["sensor_id"])
 }

 @Test
 fun `should extract sensor_id with 3 digits`() {
  // Given
  val input = "sensor_id=z123; value=90"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertEquals("z123", result["sensor_id"])
 }

 @Test
 fun `should not extract invalid sensor_id format - starts with number`() {
  // Given
  val input = "sensor_id=1a; value=90"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertNull(result["sensor_id"])
  assertEquals("90", result["value"])
 }


 @Test
 fun `should extract value field only when sensor_id is invalid`() {
  // Given
  val input = "sensor_id=1a; value=90"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertNull(result["sensor_id"])
  assertEquals("90", result["value"])
 }

 @Test
 fun `should extract sensor_id field only when value is invalid`() {
  // Given
  val input = "sensor_id=a12; value=abc"

  // When
  val result = extractor.extractSensorData(input)

  // Then
  assertEquals("a12", result["sensor_id"])
  assertNull(result["value"])
 }
}