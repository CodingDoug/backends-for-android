package com.hyperaware.bfa.cloudrun.util

import com.google.api.core.ApiFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.ExecutionException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Converts an ApiFuture returned by Google Cloud APIs into a Deferrable,
 * so it can be used in suspending functions with Kotlin coroutines.
 */

suspend fun <T> ApiFuture<T>.await(
    deepCancel: Boolean = false
): T = suspendCancellableCoroutine { co ->
    if (deepCancel) {
        co.invokeOnCancellation {
            cancel(true)
        }
    }
    addListener(Runnable {
        if (isCancelled) {
            co.cancel()
        }
        else {
            try {
                co.resume(get())
            }
            catch (ee: ExecutionException) {
                co.resumeWithException(ee.cause!!)
            }
        }
    }, MoreExecutors.directExecutor())
}
