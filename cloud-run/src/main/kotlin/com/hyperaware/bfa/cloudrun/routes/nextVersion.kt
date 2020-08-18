package com.hyperaware.bfa.cloudrun.routes

import com.hyperaware.bfa.common.model.AnObject
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

fun Routing.nextVersion() {

    post("/nextVersion") {
        application.environment.log.info("/nextVersion")

        // Parse the POST body input text as JSON with KotlinX serialization
        val bodyText = call.receiveText()
        val inputObject = try {
            Json.decodeFromString(AnObject.serializer(), bodyText)
        }
        catch (e: SerializationException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        // Update the version of the object and serialize it back to the client
        val outputObject = inputObject.copy(
            version = inputObject.version + 1
        )
        val responseText = Json.encodeToString(AnObject.serializer(), outputObject)
        call.respondText(responseText, ContentType.Application.Json)
    }

}
