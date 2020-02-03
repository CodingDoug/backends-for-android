package com.hyperaware.bfa.common.model

import kotlinx.serialization.Serializable

/**
 * Exact same data class as seen in the Android client. For your project,
 * shared classes should live in a library that both client and server use.
 */

@Serializable
data class AnObject(
    val version: Int,
    val name: String
)