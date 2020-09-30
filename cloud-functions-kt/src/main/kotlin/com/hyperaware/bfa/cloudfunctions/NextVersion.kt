package com.hyperaware.bfa.cloudfunctions

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import com.hyperaware.bfa.common.model.AnObject
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@Suppress("unused")
class NextVersion : HttpFunction {
    @Throws(Exception::class)
    override fun service(request: HttpRequest, response: HttpResponse) {
        val input = request.reader.readText()
        val inputObject = try {
            Json.decodeFromString(AnObject.serializer(), input)
        }
        catch (e: SerializationException) {
            response.setStatusCode(400)
            return
        }
        val outputObject = inputObject.copy(
            version = inputObject.version + 1
        )
        val responseText = Json.encodeToString(AnObject.serializer(), outputObject)
        response.writer.write(responseText)
    }
}
