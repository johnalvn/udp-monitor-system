package integration.handler

import co.jedal.test.integration.handler.AlertDispatcher
import co.jedal.test.integration.handler.dto.SensorMsgBrokerDTO
import co.jedal.test.integration.handler.sensor.SensorHandler
import co.jedal.test.integration.handler.sensor.SensorTypeEnum
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class AlertDispatcherTest {

 private lateinit var alertDispatcher: AlertDispatcher

 @BeforeEach
 fun setup() {
  alertDispatcher = AlertDispatcher()
 }

 @Test
 fun `test dispatch should call handle method on sensor`() {
  // Arrange
  val mockSensor = Mockito.mock(SensorHandler::class.java)

  // Act
  alertDispatcher.dispatch(mockSensor)

  // Assert
  verify(mockSensor, times(1)).handle()
 }

 @Test
 fun `test getSensorType should return TEMPERATURE when source starts with t`() {
  // Arrange
  val sensorMsgDTO = SensorMsgBrokerDTO(
   msgId = "test-id",
   source = "t1",
   value = "40", // Above threshold
   dateTime = "2023-06-15T14:30:00"
  )

  // Act
  val result = alertDispatcher.getSensorType(sensorMsgDTO)

  // Assert
  assert(result == SensorTypeEnum.TEMPERATURE)
 }

 @Test
 fun `test getSensorType should return TEMPERATURE when source starts with T (uppercase)`() {
  // Arrange
  val sensorMsgDTO = SensorMsgBrokerDTO(
   msgId = "test-id",
   source = "T1",
   value = "40", // Above threshold
   dateTime = "2023-06-15T14:30:00"
  )

  // Act
  val result = alertDispatcher.getSensorType(sensorMsgDTO)

  // Assert
  assert(result == SensorTypeEnum.TEMPERATURE)
 }

 @Test
 fun `test getSensorType should return HUMIDITY when source starts with h`() {
  // Arrange
  val sensorMsgDTO = SensorMsgBrokerDTO(
   msgId = "test-id",
   source = "h1",
   value = "40", // Above threshold
   dateTime = "2023-06-15T14:30:00"
  )

  // Act
  val result = alertDispatcher.getSensorType(sensorMsgDTO)

  // Assert
  assert(result == SensorTypeEnum.HUMIDITY)
 }

 @Test
 fun `test getSensorType should return HUMIDITY when source starts with H (uppercase)`() {
  // Arrange
  val sensorMsgDTO = SensorMsgBrokerDTO(
   msgId = "test-id",
   source = "H1",
   value = "40", // Above threshold
   dateTime = "2023-06-15T14:30:00"
  )

  // Act
  val result = alertDispatcher.getSensorType(sensorMsgDTO)

  // Assert
  assert(result == SensorTypeEnum.HUMIDITY)
 }

 @Test
 fun `test getSensorType should return NO_IDENTIFIER for other sources`() {
  // Arrange
  val sensorMsgDTO = SensorMsgBrokerDTO(
   msgId = "test-id",
   source = "r1",
   value = "40", // Above threshold
   dateTime = "2023-06-15T14:30:00"
  )

  // Act
  val result = alertDispatcher.getSensorType(sensorMsgDTO)

  // Assert
  assert(result == SensorTypeEnum.NO_IDENTIFIER)
 }

}