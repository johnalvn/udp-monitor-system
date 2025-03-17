package processor.serializer

import co.jedal.test.processor.dto.SensorMsgBrokerDTO
import co.jedal.test.processor.serializer.SensorMsgParser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertNotNull

class SensorMsgParserTest {

 private lateinit var sensorMsgParser: SensorMsgParser

 @BeforeEach
 fun setUp() {
  sensorMsgParser = SensorMsgParser()
 }

 @Test
 fun `test parse method with valid message`() {
  // Given
  val validMessage = """sensor_id=t1; value=90"""

  // When
  val result = sensorMsgParser.parse(validMessage)

  // Then
  assertNotNull(result)
  assertEquals("t1", result.source)
  assertEquals("90", result.value)
  assertNotNull(result.msgId)
  assertNotNull(result.dateTime)
 }
}