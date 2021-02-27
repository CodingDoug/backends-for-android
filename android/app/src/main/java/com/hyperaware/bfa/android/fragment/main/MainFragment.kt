package com.hyperaware.bfa.android.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hyperaware.bfa.android.R
import com.hyperaware.bfa.android.fragment.callablesum.CallableSumFragment
import com.hyperaware.bfa.android.fragment.cloudrunendpoint.CloudRunEndpointFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavButton(R.id.btnInvokeCallableSum)
        initNavButton(R.id.btnInvokeCloudRunEndpoint)
    }

    private fun initNavButton(@IdRes id: Int) {
        view!!.findViewById<View>(id).setOnClickListener(navButtonOnClickListener)
    }

    private val navButtonOnClickListener = View.OnClickListener { view ->
        onNavigationButtonClick(view)
    }

    private fun onNavigationButtonClick(view: View) {
        val fragment = when (view.id) {
            R.id.btnInvokeCallableSum -> CallableSumFragment.newInstance()
            R.id.btnInvokeCloudRunEndpoint -> CloudRunEndpointFragment.newInstance()
            else -> throw IllegalArgumentException()
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
