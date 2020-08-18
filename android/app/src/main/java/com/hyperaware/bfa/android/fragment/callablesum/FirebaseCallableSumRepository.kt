package com.hyperaware.bfa.android.fragment.callablesum

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.firebase.functions.FirebaseFunctions
import com.hyperaware.bfa.android.util.Failure
import com.hyperaware.bfa.android.util.Loading
import com.hyperaware.bfa.android.util.Resource
import com.hyperaware.bfa.android.util.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class FirebaseCallableSumRepository : SumRepository {

    companion object {
        private val functions = FirebaseFunctions.getInstance()
        private val loading = Loading<Int>()
    }

    override fun computeSum(a: Int, b: Int): LiveData<Resource<Int>> {
        return liveData(Dispatchers.IO) {
            emit(loading)

            try {
                val params = mapOf("a" to a, "b" to b)
                val data = functions.getHttpsCallable("sum")
                    .call(params)
                    .await()
                    .data

                // Check that response was in the expected format
                // (JSON object with an integer property called "sum")
                //
                if (data is Map<*, *>) {
                    val sum = data["sum"]
                    if (sum is Int) {
                        emit(Success(sum))
                    }
                    else {
                        val type = sum?.javaClass?.toString() ?: "null"
                        emit(Failure<Int>(Exception("Sum was not an integer (was instead $type)")))
                    }
                }
                else {
                    val type = data?.javaClass?.toString() ?: "null"
                    emit(Failure<Int>(Exception("Response object was not a Map (was instead $type)")))
                }
            }
            catch (e: Exception) {
                emit(Failure<Int>(e))
            }
        }
    }

}
