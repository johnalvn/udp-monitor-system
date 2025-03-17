package co.jedal.test.monitor.sensor

import co.jedal.test.monitor.sensor.dto.UdpMessage
import kotlinx.coroutines.flow.Flow


interface Monitoreable {
    fun monitor(): Flow<UdpMessage>
}