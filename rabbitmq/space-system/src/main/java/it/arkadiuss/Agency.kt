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
        val messagesQueueName = channel.queueDeclare().queue;
        channel.queueBind(messagesQueueName, Settings.MESSAGES_EXCHANGE, "agency.${id}")
        channel.basicConsume(messagesQueueName, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleMessage(String(it)) }
            }
        })
    }

    private fun setupConfirmationQueue() {
        val confirmationQueueName = channel.queueDeclare(Settings.SERVICES_EXCHANGE_AGENCY_QUEUE, false, false, false, null).queue
        channel.queueBind(confirmationQueueName, Settings.SERVICES_EXCHANGE, "agency.${id}")
        channel.basicConsume(confirmationQueueName, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleConfirmation(String(it)) }
            }
        })
    }

    private fun handleMessage(message: String) {
        println("Handling message: $message")
    }

    private fun handleConfirmation(message: String) {
        println("Received confirmation: $message")
    }

    private fun nextJobId(): Int {
        return id*100+(lastJobId++)
    }

    fun run() {
        id = readLine()?.toInt() ?: return
        channel = createChannel()

        setupExchanges()
        setupMessagesQueue()
        setupConfirmationQueue()

        while(true) {
            val commandNo = readLine()?.toInt() ?: continue
            val command = Settings.SERVICES[commandNo];
            val jobId = nextJobId();
            val key = "services.${command}";
            channel.basicPublish(Settings.SERVICES_EXCHANGE, key, null, byteArrayOf(jobId.toByte()))
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