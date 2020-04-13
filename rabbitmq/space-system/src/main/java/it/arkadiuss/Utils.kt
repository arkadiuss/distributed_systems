package it.arkadiuss

import java.time.LocalDateTime

fun log(message: String) {
    println("${LocalDateTime.now()}: $message")
}