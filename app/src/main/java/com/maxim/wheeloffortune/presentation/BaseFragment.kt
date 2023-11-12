package com.maxim.wheeloffortune.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.maxim.wheeloffortune.App
import com.maxim.wheeloffortune.presentation.edit.EditCommunication
import com.maxim.wheeloffortune.presentation.edit.EditViewModel
import com.maxim.wheeloffortune.presentation.main.Communication
import com.maxim.wheeloffortune.presentation.main.MainViewModel

abstract class BaseFragment : Fragment() {
    protected lateinit var communication: Communication
    protected lateinit var viewModel: MainViewModel
    protected lateinit var editCommunication: EditCommunication
    protected lateinit var editViewModel: EditViewModel

    protected fun replaceFragment(
        fragment: Fragment,
        addToBackStack: Boolean = false,
        showHomeButton: Boolean
    ) {
        (requireActivity() as FragmentManager).replaceFragment(
            fragment,
            addToBackStack,
            showHomeButton
        )
    }

    protected fun popBackStack() {
        (requireActivity() as FragmentManager).popBackStack()
    }

    protected fun requestFocus(editText: EditText) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        editText.requestFocus()
        imm.showSoftInput(editText, 0)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed.invoke()
        return true
    }
}