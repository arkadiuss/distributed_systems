package it.arkadiuss

object Settings {
    // Services
    const val SERVICES_EXCHANGE = "services_exchange"
    val SERVICES_EXCHANGE_SERVICES_QUEUES = arrayOf(
            "services_exchange_people_transport_queue",
            "services_exchange_cargo_transport_queue",
            "services_exchange_satellite_setup_queue")
    const val SERVICES_EXCHANGE_ADMIN_QUEUE = "services_exchange_admin_queue"
    // Messages
    const val MESSAGES_EXCHANGE = "messages_exchange"
    private const val MESSAGES_EXCHANGE_AGENCY_QUEUE_PREFIX = "messages_exchange_agency_"
    private const val MESSAGES_EXCHANGE_CARRIER_QUEUE_PREFIX = "messages_exchange_carrier_"
    private const val SERVICES_EXCHANGE_AGENCY_QUEUE_PREFIX = "services_exchange_agency_queue_"

    fun getMessageExchangeAgencyQueue(id: Int): String {
        return "${MESSAGES_EXCHANGE_AGENCY_QUEUE_PREFIX}${id}"
    }

    fun getMessageExchangeCarrierQueue(id: Int): String {
        return "${MESSAGES_EXCHANGE_CARRIER_QUEUE_PREFIX}${id}"
    }

    fun getServiceExchangeAgencyQueue(id: Int): String {
        return "${SERVICES_EXCHANGE_AGENCY_QUEUE_PREFIX}${id}"
    }

    val SERVICES = arrayOf("people_transport", "cargo_transport", "satellite_setup")
}