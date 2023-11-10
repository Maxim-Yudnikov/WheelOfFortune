package com.maxim.wheeloffortune.presentation.main

import android.widget.TextView
import java.io.Serializable

interface UiItem {
    fun showText(textView: TextView)
    fun same(item: UiItem): Boolean
    fun sameContent(item: UiItem): Boolean
    fun onClick(listener: RecyclerViewAdapter.Listener)
    data class BaseUiWheel(
        private val id: Int,
        private val title: String,
        private val itemList: List<BaseUiItem>
    ) : UiItem {
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

    data class BaseUiItem(private val name: String, private val color: Int) : UiItem, Serializable {
        override fun showText(textView: TextView) {
            textView.text = name
        }

        override fun same(item: UiItem): Boolean {
            return item is BaseUiItem && item.name == name && item.color == color
        }

        override fun sameContent(item: UiItem): Boolean {
            return item is BaseUiItem && item.name == name && item.color == color
        }

        override fun onClick(listener: RecyclerViewAdapter.Listener) {
            TODO("Not yet implemented")
        }
    }

    object Empty : UiItem {
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
}