package com.hyperaware.bfa.cloudrun

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.hyperaware.bfa.cloudrun.routes.getDocument
import com.hyperaware.bfa.cloudrun.routes.nextVersion
import com.hyperaware.bfa.cloudrun.routes.ping
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val port = System.getenv("PORT") ?: "8080"
    println("Running ktor on port $port")

    initEnvironment()
    initFirebase()

    // Watch for class changes when not running locally, not in Cloud Run
    //
    val watchPaths = when (System.getenv("K_SERVICE")) {
        null -> listOf(
            "out/production",   // when running in IntelliJ
            "build/classes"     // when running with gradle
        )
        else -> emptyList()
    }

    embeddedServer(
        factory = Netty,
        port = port.toInt(),
        watchPaths = watchPaths,
        module = Application::cloudRunBackend
    ).start(wait = true)
}

fun Application.cloudRunBackend() {
    install(ContentNegotiation) {
        // Used by getDocument to automatically serialize a Map to JSON
        gson {}
    }

    install(Routing) {
        ping()
        getDocument()
        nextVersion()
    }
}

private fun initEnvironment() {
    // TODO: Instead of depending on GOOGLE_CLOUD_PROJECT env var set by
    //  gcloud during deployment, get project ID using Google Cloud metadata
    //  servers. See:
    //  https://cloud.google.com/appengine/docs/standard/java/accessing-instance-metadata
}

private fun initFirebase() {
    // This env var is set by the gcloud command line at deployment and
    // contains the ID of the current Google Cloud project at runtime.
    // This allows the Firebase Admin SDK to initialize without any other
    // configuration.
    val projectId = System.getenv("GOOGLE_CLOUD_PROJECT")
    val app = if (projectId != null) {
        // Init with Google Cloud default credentials when deployed to Cloud Run.
        initFirebaseDefault(projectId)
    }
    else {
        // Init with service account credentials when running locally.
        initFirebaseServiceAccount()
    }

    println("Initialized Firebase app ${app.name}")
}

private fun initFirebaseDefault(projectId: String): FirebaseApp {
    println("Using default credentials for project ID: $projectId")
    return FirebaseApp.initializeApp()
}

private fun initFirebaseServiceAccount(): FirebaseApp {
    // This loads the service account credentials file from resources bundled in the classpath,
    // which you will need to provide in src/main/resources/service-account.json
    println("Loading service account credentials from service-account.json")
    val classLoader = FirebaseApp::class.java.classLoader
    val resource = classLoader.getResource("service-account.json")
    if (resource == null) {
        throw IllegalArgumentException("file is not found!")
    }
    else {
        return resource.openStream().use { inputStream ->
            val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build()
            FirebaseApp.initializeApp(options)
        }
    }
}
