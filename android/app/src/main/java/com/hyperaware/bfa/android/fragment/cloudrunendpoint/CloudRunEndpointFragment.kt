package com.hyperaware.bfa.android.fragment.cloudrunendpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hyperaware.bfa.android.databinding.FragmentInvokeCallableSumBinding
import com.hyperaware.bfa.android.databinding.FragmentInvokeCloudRunEndpointBinding

class CloudRunEndpointFragment : Fragment() {

    companion object {
        fun newInstance() = CloudRunEndpointFragment()
    }

    private val viewModel: CloudRunEndpointViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInvokeCloudRunEndpointBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}
