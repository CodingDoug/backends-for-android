package com.hyperaware.bfa.android.fragment.cloudrunendpoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.hyperaware.bfa.android.util.Failure
import com.hyperaware.bfa.android.util.Loading
import com.hyperaware.bfa.android.util.Resource
import com.hyperaware.bfa.android.util.Success
import com.hyperaware.bfa.common.model.AnObject
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

class CloudRunEndpointRepository(private val cloudRunRoot: String) {

    companion object {
        private val loading = Loading<AnObject>()
        private val client = HttpClient()
    }

    fun getNextVersion(v: Int): LiveData<Resource<AnObject>> {
        return liveData(Dispatchers.IO) {
            emit(loading)

            try {
                // Create the object to send and use KotlinX to serialize it
                val outputObject = AnObject(v, "some name")
                val bodyText = Json.encodeToString(AnObject.serializer(), outputObject)

                // Receive the response and parse it
                val responseText = client.post<String> {
                    url("${cloudRunRoot}/nextVersion")
                    body = TextContent(bodyText, ContentType.Application.Json)
                }
                val inputObject = Json.decodeFromString(AnObject.serializer(), responseText)

                emit(Success(inputObject))
            }
            catch (e: Exception) {
                emit(Failure<AnObject>(e))
            }
        }
    }

}
