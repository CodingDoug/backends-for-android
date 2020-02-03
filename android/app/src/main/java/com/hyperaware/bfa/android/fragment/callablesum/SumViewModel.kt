package com.hyperaware.bfa.android.fragment.callablesum

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hyperaware.bfa.android.R
import com.hyperaware.bfa.android.util.Failure
import com.hyperaware.bfa.android.util.Loading
import com.hyperaware.bfa.android.util.Success

class SumViewModel(private val a: Int, private val b: Int) : ViewModel() {

    sealed class SumState {
        object Loading : SumState()
        data class Loaded(val sum: Int) : SumState()
        object Error : SumState()
    }

    // Should use DI here
//    private val repo = ObviousSumRepository()
    private val repo = FirebaseCallableSumRepository()

    val computedSum: LiveData<SumState>
        get() = _computedSumLiveData

    private val _computedSumLiveData = liveData<SumState> {
        val computeSumLiveData = repo.computeSum(a, b)
        val xformedLiveData = Transformations.map(computeSumLiveData) { result ->
            when (result) {
                is Loading ->
                    SumState.Loading
                is Success -> {
                    Log.d("@@@@", "Got sum: ${result.data}")
                    SumState.Loaded(result.data!!)
                }
                is Failure -> {
                    // Should log to Crashlytics here if this is unexpected
                    Log.e("@@@@", "Exception computing sum", result.e)
                    SumState.Error
                }
            }
        }
        emitSource(xformedLiveData)
    }

}
