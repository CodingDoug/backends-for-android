package com.hyperaware.bfa.android.fragment.callablesum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hyperaware.bfa.android.databinding.FragmentInvokeCallableSumBinding

class CallableSumFragment : Fragment() {

    companion object {
        fun newInstance() = CallableSumFragment()
    }

    private class SumViewModelFactory(private val a: Int, private val b: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SumViewModel(a, b) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val viewModel: SumViewModel by viewModels { SumViewModelFactory(2, 3) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInvokeCallableSumBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}
