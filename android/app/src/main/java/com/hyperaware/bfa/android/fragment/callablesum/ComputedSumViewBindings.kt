package com.hyperaware.bfa.android.fragment.callablesum

import android.util.Log
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.databinding.BindingAdapter
import com.hyperaware.bfa.android.R

@BindingAdapter("computedSum")
fun updateSumTextView(tv: TextView, sumState: SumViewModel.SumState?) {
    val context = tv.context

    // This happens on initial load before a LiveData emits a value
    if (sumState == null) {
        return
    }

    val text: String
    @StyleRes val textAppearance: Int

    when (sumState) {
        SumViewModel.SumState.Loading -> {
            text = context.getString(R.string.sumLoading)
            textAppearance = R.style.SumLoadingTextAppearance
        }
        is SumViewModel.SumState.Loaded -> {
            text = sumState.sum.toString()
            textAppearance = R.style.SumLoadedTextAppearance
        }
        SumViewModel.SumState.Error -> {
            text = context.getString(R.string.sumError)
            textAppearance = R.style.SumErrorTextAppearance
        }
    }

    tv.text = text
    @Suppress("DEPRECATION")
    tv.setTextAppearance(tv.context, textAppearance)
}
