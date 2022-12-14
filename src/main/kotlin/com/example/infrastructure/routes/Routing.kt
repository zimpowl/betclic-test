package com.example.infrastructure.routes

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureRouting() {

    install(ContentNegotiation) {
        json()
    }

    routing {
        playerRouting()
    }
}
