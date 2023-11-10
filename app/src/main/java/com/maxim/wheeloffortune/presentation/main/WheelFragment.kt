package com.maxim.wheeloffortune.presentation.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.BaseFragment

class WheelFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wheel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = mutableListOf<UiItem.BaseUiItem>()
        for (i in 0..Int.MAX_VALUE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if(requireArguments().getSerializable("$LIST$i", UiItem.BaseUiItem::class.java) != null) {
                    list.add(requireArguments().getSerializable("$LIST$i", UiItem.BaseUiItem::class.java)!!)
                } else
                    break
            } else {
                if(requireArguments().getSerializable("$LIST$i") != null) {
                    list.add(requireArguments().getSerializable("$LIST$i") as UiItem.BaseUiItem)
                } else
                    break
            }
        }

        val itemsTextView = view.findViewById<TextView>(R.id.itemsTextView)
        val resultTextView = view.findViewById<TextView>(R.id.resultTextView)
        val actionButton = view.findViewById<Button>(R.id.actionButton)

        val nameList = mutableListOf<String>()
        list.forEach {
            it.showText(itemsTextView)
            nameList.add(itemsTextView.text.toString())
        }
        val sb = StringBuilder()
        nameList.forEach {
            sb.append("$it\n")
        }
        itemsTextView.text = sb
        actionButton.setOnClickListener {
            viewModel.rotate()
        }
        viewModel.observe(this) {
            it.apply(resultTextView, actionButton)
        }
    }

    companion object {
        private const val TITLE = "TITLE"
        private const val LIST = "LIST"
        fun newInstance(title: String, list: List<UiItem.BaseUiItem>): WheelFragment {
            val fragment = WheelFragment()
            Bundle().also {
                it.putString(TITLE, title)
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
        replaceFragment(MainFragment())
    }
}
