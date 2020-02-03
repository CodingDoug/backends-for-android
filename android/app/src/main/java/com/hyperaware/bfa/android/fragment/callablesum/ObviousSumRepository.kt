package com.hyperaware.bfa.android.fragment.callablesum

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.hyperaware.bfa.android.util.Resource
import com.hyperaware.bfa.android.util.Success
import kotlinx.coroutines.delay

// The only sane way to add to numbers using the repository pattern.

class ObviousSumRepository : SumRepository {

    override fun computeSum(a: Int, b: Int): LiveData<Resource<Int>> {
        return liveData<Resource<Int>> {
            delay(1000)  // for dramatic effect!
            emit(Success(a + b))
        }
    }

}
