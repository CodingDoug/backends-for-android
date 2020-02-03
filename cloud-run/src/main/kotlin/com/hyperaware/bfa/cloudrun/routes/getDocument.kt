package com.hyperaware.bfa.cloudrun.routes

import com.google.firebase.cloud.FirestoreClient
import com.hyperaware.bfa.cloudrun.util.await
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

// Example of using the Firebase Admin SDK to get a document from Firestore
// and return it to the client.

fun Routing.getDocument() {
    get("/get/{id}") {
        try {
            application.environment.log.info("/get")

            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing id")
                return@get
            }

            val snapshot = FirestoreClient.getFirestore()
                    .collection("kotlin")
                    .document(id)
                    .get().await()
            if (snapshot.exists()) {
                // The Map in snapshot.data will be automatically serialized
                // as JSON in the response using the gson ContentNegotiation.
                call.respond(snapshot.data!!)
            }
            else {
                call.respond(HttpStatusCode.NotFound, "Document $id not found")
            }
        }
        catch (e: Exception) {
            application.environment.log.error("oops", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
