package com.maxim.wheeloffortune.presentation.main

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.WheelItem
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.BaseFragment
import com.maxim.wheeloffortune.presentation.edit.EditFragment

class WheelFragment : BaseFragment() {
    private lateinit var title: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wheel, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.wheel_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editWheel -> {
                replaceFragment(EditFragment.newInstance(title), true, showHomeButton = true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override var actionBarTitle = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = arguments?.getString(TITLE)!!
        actionBarTitle = title
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val list = mutableListOf<UiItem.BaseUiItem>()
        for (i in 0..Int.MAX_VALUE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (requireArguments().getSerializable(
                        "$LIST$i",
                        UiItem.BaseUiItem::class.java
                    ) != null
                ) {
                    list.add(
                        requireArguments().getSerializable(
                            "$LIST$i",
                            UiItem.BaseUiItem::class.java
                        )!!
                    )
                } else
                    break
            } else {
                if (requireArguments().getSerializable("$LIST$i") != null) {
                    list.add(requireArguments().getSerializable("$LIST$i") as UiItem.BaseUiItem)
                } else
                    break
            }
        }

        if (arguments?.getInt(WHEEL_ID) != -1)
            viewModel.openItem(requireArguments().getInt(WHEEL_ID))

        val wheel = view.findViewById<LuckyWheel>(R.id.wheel)
        val resultTextView = view.findViewById<TextView>(R.id.resultTextView)
        val actionButton = view.findViewById<Button>(R.id.actionButton)

        val wheelItemsList = mutableListOf<WheelItem>()
        list.forEach { item ->
            val data = item.getData()
            val wheelItem = WheelItem(0,
                ResourcesCompat.getColor(resources, data.second, null), data.first
            )

            wheelItemsList.add(wheelItem)
        }
        wheel.addWheelItems(wheelItemsList)

        actionButton.setOnClickListener {
            viewModel.rotate(wheel, list)
        }
        viewModel.observe(this) {
            it.apply(resultTextView, actionButton, requireContext().resources)
        }
    }

    companion object {
        private const val TITLE = "TITLE"
        private const val LIST = "LIST"
        private const val WHEEL_ID = "WHEEL_ID"
        fun newInstance(id: Int, title: String, list: List<UiItem.BaseUiItem>): WheelFragment {
            val fragment = WheelFragment()
            Bundle().also {
                it.putString(TITLE, title)
                it.putInt(WHEEL_ID, id)
                list.forEachIndexed { index, _ ->
                    it.putSerializable("$LIST$index", list[index])
                }
                fragment.arguments = it
            }
            return fragment
        }
    }

    override val onBackPressed = {
        viewModel.closeItem()
        replaceFragment(MainFragment(), showHomeButton = false)
    }
}
