package co.jedal.test.integration.rabbitmq

import com.rabbitmq.client.ConnectionFactory


class RabbitmqProducer {


    fun sendData(data: String) {
        val factory = ConnectionFactory().apply {
            host = "rabbitmq"
            username = "user"
            password = "password"
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