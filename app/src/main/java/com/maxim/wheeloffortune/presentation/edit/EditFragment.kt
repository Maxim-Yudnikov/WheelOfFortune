package com.maxim.wheeloffortune.presentation.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.BaseFragment
import com.maxim.wheeloffortune.presentation.main.MainFragment

class EditFragment() : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.framgment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        editCommunication.clear()
        val adapter =
            EditRecyclerViewAdapter(editCommunication, object : EditRecyclerViewAdapter.Listener {
                override fun onTextChanged(id: Int, text: String) {
                    editViewModel.changeItemName(id, text)
                }
            })
        recyclerView.adapter = adapter
        editViewModel.observeList(this) {
            adapter.update()
        }
        editViewModel.getItemList()

        val newItemButton = view.findViewById<Button>(R.id.newItemButton)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val titleEditText = view.findViewById<TextInputLayout>(R.id.titleEditText).editText

        newItemButton.setOnClickListener {
            editViewModel.createItem()
        }
        saveButton.setOnClickListener {
            editViewModel.endEditing(titleEditText!!.text.toString())
            replaceFragment(MainFragment())
        }
    }

    override val onBackPressed = {
        editViewModel.cancelEditing()
        replaceFragment(MainFragment())
    }
}