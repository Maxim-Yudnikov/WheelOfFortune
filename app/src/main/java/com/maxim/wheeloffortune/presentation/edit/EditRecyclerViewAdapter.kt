package com.maxim.wheeloffortune.presentation.edit

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.SimpleTextWatcher
import com.maxim.wheeloffortune.presentation.main.UiItem

class EditRecyclerViewAdapter(
    private val communication: EditCommunication,
    private val listener: Listener
) : RecyclerView.Adapter<EditRecyclerViewAdapter.ViewHolder>() {
    private val textWatchers = mutableListOf<Pair<EditText, SimpleTextWatcher>>()
    fun update() {
        //communication.getDiffResult().dispatchUpdatesTo(this)
        textWatchers.forEach {
            it.first.removeTextChangedListener(it.second)
        }
        textWatchers.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view) {
        fun bind(
            item: UiItem.BaseUiItem,
            position: Int,
            textWatchers: MutableList<Pair<EditText, SimpleTextWatcher>>
        ) {
            val nameTextView = itemView.findViewById<EditText>(R.id.nameTextView)
            item.showText(nameTextView)
            val textWatcher = object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    Log.d("MyLog", "Position: $position")
                    listener.onTextChanged(position, s.toString())
                }
            }
            textWatchers.add(Pair(nameTextView, textWatcher))
            nameTextView.addTextChangedListener(textWatcher)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int = communication.getList().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(communication.getList()[position], position, textWatchers)
    }

    interface Listener {
        fun onTextChanged(id: Int, text: String)
    }
}