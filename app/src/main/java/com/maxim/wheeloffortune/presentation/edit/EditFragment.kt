package com.maxim.wheeloffortune.presentation.edit

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.SimpleTextWatcher
import com.maxim.wheeloffortune.presentation.BaseFragment
import com.maxim.wheeloffortune.presentation.main.MainFragment

class EditFragment() : BaseFragment() {
    private lateinit var adapter: EditRecyclerViewAdapter
    private var title: String? = null
    private lateinit var titleEditText: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override var actionBarTitle = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = arguments?.getString(TITLE)
        actionBarTitle = if (title == null) "New wheel" else "Edit wheel \"$title\""
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val recyclerView = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        val listErrorTextView = view.findViewById<TextView>(R.id.listErrorTextView)
        listErrorTextView.visibility = View.GONE
        editCommunication.clear()
        adapter =
            EditRecyclerViewAdapter(editCommunication, object : EditRecyclerViewAdapter.Listener {
                override fun onTextChanged(id: Int, text: String) {
                    editViewModel.changeItemName(id, text)
                    listErrorTextView.text = ""
                    listErrorTextView.visibility = View.GONE
                }

                override fun changeColor(id: Int, color: Int) {
                    editViewModel.changeItemColor(id, color)
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
        val textInputLayout = view.findViewById<TextInputLayout>(R.id.titleEditText)
        titleEditText = textInputLayout.editText!!

        if (title != null) {
            titleEditText.setText(title)
        }

        newItemButton.setOnClickListener {
            listErrorTextView.text = ""
            listErrorTextView.visibility = View.GONE
            editViewModel.createItem()
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
        saveButton.setOnClickListener {
            editViewModel.endEditing(titleEditText.text.toString()) {
                replaceFragment(MainFragment(), showHomeButton = false)
            }
        }
        titleEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.error = ""
                textInputLayout.isErrorEnabled = false
            }
        })

        if (savedInstanceState != null)
            editViewModel.update()

        editViewModel.observeState(this) {
            it.apply(textInputLayout, listErrorTextView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TITLE, titleEditText.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            titleEditText.setText(savedInstanceState.getString(TITLE))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteWheel -> {
                AlertDialog.Builder(requireContext()).setTitle("Delete the wheel? It can't be undone.")
                    .setPositiveButton("Yes") { _, _ ->
                        editViewModel.deleteWheel()
                        replaceFragment(MainFragment(), showHomeButton = false)
                    }.setNegativeButton("No") {_,_ ->}.create().show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (title != null) {
            inflater.inflate(R.menu.edit_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override val onBackPressed: () -> Unit = {
        AlertDialog.Builder(requireContext()).setTitle("Cancel editing? All changes will be lost.")
            .setPositiveButton("Yes") { _, _ ->
                editViewModel.cancelEditing()
                adapter.clear()
                popBackStack()
            }.setNegativeButton("No") {_,_ ->}.create().show()
    }

    companion object {
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