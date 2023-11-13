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
                if (id == -1)
                    replaceFragment(EditFragment(), true, showHomeButton = true)
                else
                    replaceFragment(WheelFragment.newInstance(id, title, list), true, showHomeButton = true)
            }
        })
        recyclerView.adapter = adapter
        val spanCount = requireActivity().resources.getInteger(R.integer.grid_column_count)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        viewModel.observeList(this) {
            adapter.update()
        }
        viewModel.getItemList()
    }

    override val onBackPressed = {}
}