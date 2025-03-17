package co.jedal.test.monitor.sensor.impl

import co.jedal.test.monitor.sensor.Monitoreable
import co.jedal.test.monitor.sensor.dto.UdpMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.charset.StandardCharsets
import kotlin.coroutines.cancellation.CancellationException


class UdpSensorMonitor(private val port:Int)  : Monitoreable {

    private val socket = DatagramSocket(port)
    private val bufferSize = 1024


    override fun monitor(): Flow<UdpMessage> = flow {
        try {
            while (currentCoroutineContext().isActive) {
                val buffer = ByteArray(bufferSize)
                val packet = DatagramPacket(buffer, buffer.size)


                socket.receive(packet)


                val message = String(
                    packet.data,
                    packet.offset,
                    packet.length,
                    StandardCharsets.UTF_8
                )

                emit(
                    UdpMessage(
                    content = message,
                    senderAddress = packet.address,
                    senderPort = packet.port,
                    listenerPort = port
                )
                )
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            println("Error while listening: ${e.message}")
        } finally {
            socket.close()
        }
    }.flowOn(Dispatchers.IO)

    fun close() {
        if (!socket.isClosed) {
            socket.close()
        }
    }
}