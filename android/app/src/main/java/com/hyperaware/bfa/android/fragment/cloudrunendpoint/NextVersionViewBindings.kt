package com.hyperaware.bfa.android.fragment.cloudrunendpoint

import android.util.Log
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.databinding.BindingAdapter
import com.hyperaware.bfa.android.R

@BindingAdapter("nextVersion")
fun updateSumTextView(tv: TextView, nextVersionState: CloudRunEndpointViewModel.NextVersionState?) {
    Log.d("@@@@", "NextVersionState $nextVersionState")
    val context = tv.context

    // This happens on initial load before a LiveData emits a value
    if (nextVersionState == null) {
        return
    }

    val text: String
    @StyleRes val textAppearance: Int

    when (nextVersionState) {
        CloudRunEndpointViewModel.NextVersionState.Loading -> {
            text = context.getString(R.string.sumLoading)
            textAppearance = R.style.SumLoadingTextAppearance
        }
        is CloudRunEndpointViewModel.NextVersionState.Loaded -> {
            text = nextVersionState.anObject.toString()
            textAppearance = R.style.SumLoadedTextAppearance
        }
        CloudRunEndpointViewModel.NextVersionState.Error -> {
            text = context.getString(R.string.sumError)
            textAppearance = R.style.SumErrorTextAppearance
        }
    }

    tv.text = text
    @Suppress("DEPRECATION")
    tv.setTextAppearance(tv.context, textAppearance)
}
