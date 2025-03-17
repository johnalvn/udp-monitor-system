package co.jedal.test.integration.rabbitmq

import co.jedal.test.integration.handler.SensorRawMessageHandler
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import java.nio.charset.StandardCharsets

class Consumer {

    fun consumeFromExchange() {
        val factory = ConnectionFactory().apply {
            host = "rabbitmq"
            username = "user"
            password = "password"
        }

        val connection = factory.newConnection()
        val channel = connection.createChannel()

        val exchangeName = "logs"
        val exchangeType = "fanout"
        channel.exchangeDeclare(exchangeName, exchangeType)

        val queueName = channel.queueDeclare().queue

        channel.queueBind(queueName, exchangeName, "")

        println(" [*] Waiting for messages from exchange '$exchangeName'. To exit press CTRL+C")

        val deliverCallback = DeliverCallback { _, delivery ->
            val message = String(delivery.body, StandardCharsets.UTF_8)
            println(" [x] Received '$message'")
            SensorRawMessageHandler().handler(message)
        }

        // Start consuming (auto-ack = true)
        channel.basicConsume(queueName, true, deliverCallback) { _ -> }
    }
}