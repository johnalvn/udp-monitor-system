package co.jedal.test.listener

import co.jedal.test.integration.rabbitmq.RabbitmqProducer
import co.jedal.test.processor.Processor
import co.jedal.test.processor.SensorMessageProcessor
import co.jedal.test.monitor.sensor.impl.UdpSensorMonitor
import co.jedal.test.validator.pre.SensorMsgPreValidators
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class Listener {

    fun listenMultiple(listOfListeners :List<UdpSensorMonitor>) = runBlocking{

        println("Press Ctrl+C to stop the application")

        val mergedFlow = merge(*listOfListeners.map { it.monitor() }.toTypedArray())


        val job = launch {
            mergedFlow
                .onEach { message ->
                    println("Listener on port ${message.listenerPort} received: '${message.content.trim()}' from ${message.senderAddress}:${message.senderPort}")
                    if (SensorMsgPreValidators().execute(message.content.trim())){
                        println("message format passed")
                        message.content.let {
                            SensorMessageProcessor().process(message.content.trim())
                        }
                    }
                }
                .catch { e ->
                    println("Error in flow: ${e.message}")
                }
                .collect()
        }
        return@runBlocking job
    }


}