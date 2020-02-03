package com.hyperaware.bfa.cloudrun.routes

import com.hyperaware.bfa.common.model.AnObject
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.post
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

private val json = Json(JsonConfiguration.Stable)

fun Routing.nextVersion() {

    post("/nextVersion") {
        application.environment.log.info("/nextVersion")

        // Parse the POST body input text as JSON with KotlinX serialization
        val bodyText = call.receiveText()
        val inputObject = try {
            json.parse(AnObject.serializer(), bodyText)
        }
        catch (e: SerializationException) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        // Update the version of the object and serialize it back to the client
        val outputObject = inputObject.copy(
            version = inputObject.version + 1
        )
        val responseText = json.stringify(AnObject.serializer(), outputObject)
        call.respondText(responseText, ContentType.Application.Json)
    }

}
