package com.hyperaware.bfa.android.fragment.callablesum

import androidx.lifecycle.LiveData
import com.hyperaware.bfa.android.util.Resource

interface SumRepository {
    fun computeSum(a: Int, b: Int): LiveData<Resource<Int>>
}
