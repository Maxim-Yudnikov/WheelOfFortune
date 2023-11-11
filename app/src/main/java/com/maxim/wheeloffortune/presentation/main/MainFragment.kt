package com.maxim.wheeloffortune.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.BaseFragment
import com.maxim.wheeloffortune.presentation.edit.EditFragment

class MainFragment() : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override var actionBarTitle = "Wheel of fortune"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = RecyclerViewAdapter(communication, object : RecyclerViewAdapter.Listener {
            override fun onClick(id: Int, title: String, list: List<UiItem.BaseUiItem>) {
                viewModel.openItem(id)
                if (id == -1)
                    replaceFragment(EditFragment(), true)
                else
                    replaceFragment(WheelFragment.newInstance(title, list), true)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.observeList(this) {
            adapter.update()
        }
        viewModel.getItemList()
    }

    override val onBackPressed = {}
}