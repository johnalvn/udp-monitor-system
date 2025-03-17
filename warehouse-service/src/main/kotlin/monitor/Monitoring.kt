package co.jedal.test.monitor

import co.jedal.test.listener.Listener
import co.jedal.test.monitor.sensor.impl.UdpSensorMonitor
import kotlinx.coroutines.runBlocking

class Monitoring {

    private val temperatureSensorPort = 3344
    private val humiditySensorPort = 3355

    fun startMonitoring() = runBlocking {
        println("Monitoring started")

        val temperatureSensor = UdpSensorMonitor(temperatureSensorPort)
        val humiditySensor = UdpSensorMonitor(humiditySensorPort)

        val sensors = listOf(temperatureSensor, humiditySensor)

        val  job = Listener().listenMultiple(sensors)

        Runtime.getRuntime().addShutdownHook(Thread {
            runBlocking {
                println("Shutting down...")
                job.cancel()
                sensors.map { it.close() }
            }
        })

        job.join()
    }
}