package co.jedal.test.integration.rabbitmq

import co.jedal.test.config.ConfigManager
import com.rabbitmq.client.ConnectionFactory


class RabbitmqProducer {


    fun sendData(data: String) {
        val factory = ConnectionFactory().apply {
            host = ConfigManager.get("rabbitmq.host").toString()
            username = ConfigManager.get("rabbitmq.user").toString()
            password = ConfigManager.get("rabbitmq.password").toString()
        }


        factory.newConnection().use { connection ->
            connection.createChannel().use { channel ->

                val exchangeName = "logs"
                channel.exchangeDeclare(exchangeName, "fanout")


                val message = data


                channel.basicPublish(
                    exchangeName,
                    "", // routing key (not needed for fanout)
                    null, // properties
                    message.toByteArray()
                )

                println(" [x] Sent '$message'")
            }
        }
    }
}