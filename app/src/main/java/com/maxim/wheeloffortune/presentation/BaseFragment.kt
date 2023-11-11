package com.maxim.wheeloffortune.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.maxim.wheeloffortune.App
import com.maxim.wheeloffortune.presentation.edit.EditCommunication
import com.maxim.wheeloffortune.presentation.edit.EditViewModel
import com.maxim.wheeloffortune.presentation.main.Communication
import com.maxim.wheeloffortune.presentation.main.MainViewModel

abstract class BaseFragment: Fragment() {
    protected lateinit var communication: Communication
    protected lateinit var viewModel: MainViewModel
    protected lateinit var editCommunication: EditCommunication
    protected lateinit var editViewModel: EditViewModel

    protected fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        (requireActivity() as MainActivity).replaceFragment(fragment, addToBackStack)
    }

    protected abstract val onBackPressed: () -> Unit
    protected abstract var actionBarTitle: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireActivity().application as App
        communication = app.communication
        viewModel = app.viewModel
        editCommunication = app.editCommunication
        editViewModel = app.editViewModel

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed.invoke()
            }
        })

        (requireActivity() as MainActivity).setActionBarTitle(actionBarTitle)
    }
}