package it.arkadiuss

import com.rabbitmq.client.*
import java.io.IOException
import java.util.concurrent.TimeoutException

class Agency {
    private var id: Int = 0
    private var lastJobId = 0
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

    private fun setupMessagesQueue() {
        val messagesQueueName = channel
                .queueDeclare(Settings.getMessageExchangeAgencyQueue(id), false, false, false, null).queue
        channel.queueBind(messagesQueueName, Settings.MESSAGES_EXCHANGE, "agency")
        channel.queueBind(messagesQueueName, Settings.MESSAGES_EXCHANGE, "all")
        channel.basicConsume(messagesQueueName, true, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleMessage(String(it)) }
            }
        })
    }

    private fun setupConfirmationQueue() {
        val confirmationQueueName = channel.queueDeclare(Settings.getServiceExchangeAgencyQueue(id), false, false, false, null).queue
        channel.queueBind(confirmationQueueName, Settings.SERVICES_EXCHANGE, "agency.${id}")
        channel.basicConsume(confirmationQueueName, true, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleConfirmation(it) }
            }
        })
    }

    private fun handleMessage(message: String) {
        log("Hey man! Yes, I listen. Admin's message: $message")
    }

    private fun handleConfirmation(message: ByteArray) {
        val jobId = message[0].toInt()
        val carrierId = message[1].toInt()
        log("Received confirmation for $jobId. Handled by $carrierId. Cool! Thank $carrierId!")
    }

    private fun nextJobId(): Int {
        return id*20+(lastJobId++)
    }

    fun run() {
        println("Give me some id please. Remember to make it unique!")
        id = readLine()?.toInt() ?: return
        channel = createChannel()

        setupExchanges()
        setupMessagesQueue()
        setupConfirmationQueue()

        while(true) {
            println("What service I need this time? \n 0 - people_transport, 1 - cargo_transport, 2 - satellite_setup")
            val commandNo = readLine()?.toInt() ?: continue
            val command = Settings.SERVICES[commandNo];
            val jobId = nextJobId();
            val key = "service.${command}"
            channel.basicPublish(Settings.SERVICES_EXCHANGE, key, null, byteArrayOf(jobId.toByte(), id.toByte()))
        }
    }

    companion object {
        @Throws(IOException::class, TimeoutException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Agency().run()
        }
    }
}