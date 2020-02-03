package com.hyperaware.bfa.cloudrun.routes

import io.ktor.application.application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.ping() {

    get("/ping") {
        application.environment.log.info("/ping")
        val message = "pong"
        call.respondText(message, ContentType.Text.Plain)
    }

}
