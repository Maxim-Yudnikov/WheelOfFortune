package com.maxim.wheeloffortune.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maxim.wheeloffortune.R

class RecyclerViewAdapter(
    private val communication: ListCommunication,
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    fun update() {
        communication.getDiffResult().dispatchUpdatesTo(this)
    }

    class ViewHolder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view) {
        fun bind(item: UiItem) {
            val textView = itemView.findViewById<TextView>(R.id.titleTextView)
            item.showText(textView)
            itemView.setOnClickListener { item.onClick(listener) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wheel_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount() = communication.getList().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(communication.getList()[position])
    }

    interface Listener {
        fun onClick(id: Int, title: String, list: List<UiItem.BaseUiItem>)
    }
}

class DiffUtilCallback(
    private val oldList: List<UiItem>,
    private val newList: List<UiItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].same(newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].sameContent(newList[newItemPosition])
    }
}