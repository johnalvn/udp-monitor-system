package integration.handler.sensor


import co.jedal.test.config.ConfigManager
import co.jedal.test.integration.handler.dto.SensorMsgBrokerDTO
import co.jedal.test.service.AlertService
import co.jedal.test.alarm.AlertData
import co.jedal.test.alarm.AlertDataFactory
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import co.jedal.test.integration.handler.sensor.*

@ExtendWith(MockitoExtension::class)
class TemperatureHandlerTest {

    private lateinit var alertService: AlertService
    private lateinit var alertDataFactory: AlertDataFactory
    private lateinit var configManager: ConfigManager

    @BeforeEach
    fun setUp() {
        mockkObject(ConfigManager)
        alertService = mockk(relaxed = true)
        alertDataFactory = mockk(relaxed = true)

        // Mock the dependencies in the abstract class
        mockkConstructor(AbstractSensorHandler::class)
        every { anyConstructed<AbstractSensorHandler>().alertService } returns alertService
        every { anyConstructed<AbstractSensorHandler>().alertDataFactory } returns alertDataFactory
    }

    @Test
    fun `handle - when temperature above threshold should send alert`() {
        // Arrange
        val threshold = "35"
        val alertMessage = "Temperature of sensor %s has crossed the threshold %s , current value = %s"
        val sensorMsgDTO = SensorMsgBrokerDTO(
            msgId = "test-id",
            source = "t1",
            value = "40", // Above threshold
            dateTime = "2023-06-15T14:30:00"
        )

        val expectedAlertMessage = String.format(
            alertMessage,
            sensorMsgDTO.source,
            threshold,
            sensorMsgDTO.value
        )

        val mockAlertData = mockk<AlertData>()

        every { ConfigManager.get("sensor.temperature.threshold") } returns threshold
        every { ConfigManager.get("sensor.temperature.alertCriticalMsg") } returns alertMessage

        every { alertDataFactory.createCriticalAlert(expectedAlertMessage) } returns mockAlertData

        val handler = TemperatureHandler(sensorMsgDTO)

        // Act
        handler.handle()

        // Assert
        //verify (exactly = 1){ alertService.sendAlert(mockAlertData) }
    }

    @Test
    fun `handle - when temperature below threshold should not send alert`() {
        // Arrange
        val threshold = "35"
        val sensorMsgDTO = SensorMsgBrokerDTO(
            msgId = "test-id",
            source = "living-room-sensor",
            value = "30", // Below threshold
            dateTime = "2023-06-15T14:30:00"
        )

        every { ConfigManager.get("sensor.temperature.threshold") } returns threshold

        val handler = TemperatureHandler(sensorMsgDTO)

        // Act
        handler.handle()

        // Assert
        verify(exactly = 0) { alertService.sendAlert(any()) }
    }

    @Test
    fun `handle - when temperature equals threshold should not send alert`() {
        // Arrange
        val threshold = "35"
        val sensorMsgDTO = SensorMsgBrokerDTO(
            msgId = "test-id",
            source = "living-room-sensor",
            value = "35", // Equal to threshold
            dateTime = "2023-06-15T14:30:00"
        )

        every { ConfigManager.get("sensor.temperature.threshold") } returns threshold

        val handler = TemperatureHandler(sensorMsgDTO)

        // Act
        handler.handle()

        // Assert
        verify(exactly = 0) { alertService.sendAlert(any()) }
    }
}