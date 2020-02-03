package com.hyperaware.bfa.android.fragment.cloudrunendpoint

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hyperaware.bfa.android.config.Config
import com.hyperaware.bfa.android.util.Failure
import com.hyperaware.bfa.android.util.Loading
import com.hyperaware.bfa.android.util.Success
import com.hyperaware.bfa.common.model.AnObject

class CloudRunEndpointViewModel : ViewModel() {

    sealed class NextVersionState {
        object Loading : NextVersionState()
        data class Loaded(val anObject: AnObject) : NextVersionState()
        object Error : NextVersionState()
    }

    // Should use DI here
    private val repo = CloudRunEndpointRepository(Config.get().cloudRunRoot)

    val nextVersion: LiveData<NextVersionState>
        get() = _nextVersion

    private val _nextVersion = liveData<NextVersionState> {
        val o = repo.getNextVersion(1)
        val xformedLiveData = Transformations.map(o) { result ->
            when (result) {
                is Loading ->
                    NextVersionState.Loading
                is Success -> {
                    Log.d("@@@@", "Got AnObject: ${result.data}")
                    NextVersionState.Loaded(result.data!!)
                }
                is Failure -> {
                    // Should log to Crashlytics here if this is unexpected
                    Log.e("@@@@", "Exception getting next version", result.e)
                    NextVersionState.Error
                }
            }
        }
        emitSource(xformedLiveData)
    }

}
