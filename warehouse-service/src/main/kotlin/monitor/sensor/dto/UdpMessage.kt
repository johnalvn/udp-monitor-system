package co.jedal.test.monitor.sensor.dto

import java.net.InetAddress

data class UdpMessage(
    val content: String,
    val senderAddress: InetAddress,
    val senderPort: Int,
    val listenerPort: Int
)
