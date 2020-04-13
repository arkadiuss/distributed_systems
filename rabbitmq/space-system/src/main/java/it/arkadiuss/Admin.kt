package it.arkadiuss

import com.rabbitmq.client.*

class Admin {
    private lateinit var channel: Channel

    private fun createChannel(): Channel {
        val factory = ConnectionFactory()
        factory.host = "127.0.0.1"
        val connection = factory.newConnection()
        return connection.createChannel()
    }

    private fun setupExchanges() {
        channel.exchangeDeclare(Settings.SERVICES_EXCHANGE, BuiltinExchangeType.TOPIC)
        channel.exchangeDeclare(Settings.MESSAGES_EXCHANGE, BuiltinExchangeType.TOPIC)
    }
    
    private fun setupCaptureQueue() {
        val captureQueueName = channel.queueDeclare().queue
        channel.queueBind(captureQueueName, Settings.SERVICES_EXCHANGE, "*.*")
        channel.basicConsume(captureQueueName, true, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleCapturedMessage(it) }
            }
        })
    }

    private fun run() {
        channel = createChannel()
        setupExchanges()
        setupCaptureQueue()

        loop@ while(true) {
            println("Mode:\n 1 - agencies \n 2 - carriers \n 3 - all")
            val command = readLine()?.split(" ") ?: listOf<String>()
            if (command.size < 2) {
                println("Incomplete command")
                continue;
            }

            val dest = when(command[0]) {
                "1" -> "agency"
                "2" -> "carrier"
                "3" -> "all"
                else -> continue@loop
            }

            channel.basicPublish(Settings.MESSAGES_EXCHANGE, dest, null, command[1].toByteArray())
        }
    }

    private fun handleCapturedMessage(message: ByteArray) {
        log("I'm a bad guy that listens every message. Here is the next one ${message.map { it.toInt() }}")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Admin().run()
        }
    }
}