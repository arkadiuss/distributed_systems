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
                .queueDeclare(Settings.getMessageExchangeCarrierQueue(id), false, false, false, null).queue
        channel.queueBind(messagesQueueName, Settings.MESSAGES_EXCHANGE, "carrier")
        channel.queueBind(messagesQueueName, Settings.MESSAGES_EXCHANGE, "all")
        channel.basicConsume(messagesQueueName, true, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleMessage(String(it)) }
            }
        })
    }

    private fun setupServicesQueue(serviceId: Int) {
        val serviceQueueName = channel
                .queueDeclare(Settings.SERVICES_EXCHANGE_SERVICES_QUEUES[serviceId], false, false, false, null).queue
        channel.queueBind(serviceQueueName, Settings.SERVICES_EXCHANGE, "service.${Settings.SERVICES[serviceId]}")
        channel.basicConsume(serviceQueueName, true, object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                body?.let { handleService(serviceId, it) }
            }
        })
    }

    private fun handleMessage(msg: String) {
        log("Oo admin! Good to know that you are still here. Received message: $msg")
    }

    private fun handleService(serviceId: Int, msg: ByteArray) {
        val jobId = msg[0].toInt()
        val senderId = msg[1].toInt()
        log("Wooohooo! I have a job! Handling service $serviceId with jobId $jobId from $senderId")
        log("That was easy! Service finished, sending confirmation...")
        channel.basicPublish(Settings.SERVICES_EXCHANGE, "agency.$senderId", null, byteArrayOf(jobId.toByte(), id.toByte()))
        log("Confirmation sent. All done! Hope to see you soon $senderId!")
    }

    private fun run() {
        channel = createChannel()
        setupExchanges()
        println("Can you please assign me an id? Please make it unique!")
        id = readLine()?.toInt() ?: return

        setupMessagesQueue()

        println("What first service do you want me to take care of? (0-2)")
        var serviceId = readLine()?.toInt() ?: return
        setupServicesQueue(serviceId)

        println("What second service do you want me to take care of? (0-2)")
        serviceId = readLine()?.toInt() ?: return
        setupServicesQueue(serviceId)

    }

    companion object {
        @Throws(IOException::class, TimeoutException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Carrier().run()
        }
    }
}
