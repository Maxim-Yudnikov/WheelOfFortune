package com.maxim.wheeloffortune.presentation.main

import android.util.Log
import android.widget.TextView
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.edit.EditRecyclerViewAdapter
import java.io.Serializable

abstract class UiItem {
    open fun showText(textView: TextView) {}
    open fun getData(): Pair<String, Int> = Pair("", -1)
    open fun same(item: UiItem): Boolean = false
    open fun sameContent(item: UiItem): Boolean = false
    open fun onClick(listener: RecyclerViewAdapter.Listener) {}
    open fun changeColor(id: Int, listener: EditRecyclerViewAdapter.Listener): Int = -1

    data class BaseUiWheel(
        private val id: Int,
        private val title: String,
        private val itemList: List<BaseUiItem>
    ) : UiItem() {
        override fun showText(textView: TextView) {
            textView.text = title
        }

        override fun same(item: UiItem): Boolean {
            return item is BaseUiWheel && item.id == id
        }

        override fun sameContent(item: UiItem): Boolean {
            return item is BaseUiWheel && item.id == id && item.title == title && item.itemList == itemList
        }

        override fun onClick(listener: RecyclerViewAdapter.Listener) {
            listener.onClick(id, title, itemList)
        }
    }

    data class FailedUiItem(private val message: String) : UiItem() {

    }

    data class BaseUiItem(private val name: String, private var color: Int) : UiItem(),
        Serializable {
        override fun showText(textView: TextView) {
            textView.text = name
        }

        override fun getData(): Pair<String, Int> {
            return Pair(name, getColorResourceId(color))
        }

        override fun same(item: UiItem): Boolean {
            return item is BaseUiItem && item.name == name && item.color == color
        }

        override fun sameContent(item: UiItem): Boolean {
            return item is BaseUiItem && item.name == name && item.color == color
        }

        override fun changeColor(id: Int, listener: EditRecyclerViewAdapter.Listener): Int {
            color++
            if (color == 5)
                color = 0
            listener.changeColor(id, color)
            return color
        }
    }

    object Empty : UiItem() {
        override fun showText(textView: TextView) {
            textView.text = "+"
        }
        override fun same(item: UiItem): Boolean {
            return item is Empty
        }
        override fun sameContent(item: UiItem): Boolean = true
        override fun onClick(listener: RecyclerViewAdapter.Listener) {
            listener.onClick(-1, "", emptyList())
        }
    }

    companion object {
        fun getColorResourceId(id: Int): Int {
            return when (id) {
                0 -> R.color.first
                1 -> R.color.second
                2 -> R.color.third
                3 -> R.color.fourth
                else -> R.color.fifth
            }
        }
    }
}