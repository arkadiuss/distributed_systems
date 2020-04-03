package it.arkadiuss

import com.rabbitmq.client.*
import java.io.IOException
import java.util.concurrent.TimeoutException

class Carrier {

    private var id: Int = 0
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
                .queueDeclare(Settings.getMessageExchangeCarrierQueue(id), false, false, false, null).queue;
        channel.queueBind(messagesQueueName, Settings.MESSAGES_EXCHANGE, "carrier.${id}")
        channel.basicConsume(messagesQueueName, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleMessage(String(it)) }
            }
        })
    }

    private fun setupServicesQueue(serviceId: Int) {
        val serviceQueueName = channel
                .queueDeclare(Settings.SERVICES_EXCHANGE_SERVICES_QUEUES[serviceId], false, false, false, null).queue
        channel.queueBind(serviceQueueName, Settings.SERVICES_EXCHANGE, "service.${Settings.SERVICES[serviceId]}")
        channel.basicConsume(serviceQueueName, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleService(serviceId, String(it)) }
            }
        })
    }

    private fun handleMessage(msg: String) {
        println("Received message: $msg")
    }

    private fun handleService(serviceId: Int, msg: String) {
        println("Handling service $serviceId with msg $msg")
    }

    private fun run() {
        channel = createChannel()
        setupExchanges()
        println("Id")
        id = readLine()?.toInt() ?: return

        println("Service 1 id")
        var serviceId = readLine()?.toInt() ?: return
        setupServicesQueue(serviceId)

        println("Service 2 id")
        serviceId = readLine()?.toInt() ?: return
        setupServicesQueue(serviceId)

        setupMessagesQueue()
    }

    companion object {
        @Throws(IOException::class, TimeoutException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Carrier().run()
        }
    }
}
