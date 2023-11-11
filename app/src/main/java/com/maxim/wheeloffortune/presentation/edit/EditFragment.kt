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
    private lateinit var adapter: EditRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.framgment_edit, container, false)
    }

    override var actionBarTitle = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = arguments?.getString(TITLE)
        actionBarTitle = if(title == null) "New wheel" else "Edit $title"
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        editCommunication.clear()
        adapter =
            EditRecyclerViewAdapter(editCommunication, object : EditRecyclerViewAdapter.Listener {
                override fun onTextChanged(id: Int, text: String) {
                    editViewModel.changeItemName(id, text)
                }

                override fun delete(id: Int) {
                    editViewModel.deleteItem(id)
                }
            })
        recyclerView.adapter = adapter
        editViewModel.observeList(this) {
            adapter.update()
        }
        editViewModel.getItemList()

        val newItemButton = view.findViewById<Button>(R.id.newItemButton)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val titleEditText = view.findViewById<TextInputLayout>(R.id.titleEditText).editText!!

        if(title != null) titleEditText.setText(title)

        newItemButton.setOnClickListener {
            editViewModel.createItem()
        }
        saveButton.setOnClickListener {
            editViewModel.endEditing(titleEditText.text.toString())
            replaceFragment(MainFragment())
        }
    }

    override val onBackPressed = {
        editViewModel.cancelEditing()
        adapter.clear()
        replaceFragment(MainFragment())
    }

    companion object  {
        private const val TITLE = "TITLE"
        fun newInstance(title: String): EditFragment {
            val fragment = EditFragment()
            Bundle().also {
                it.putString(TITLE, title)
                fragment.arguments = it
            }
            return fragment
        }
    }
}