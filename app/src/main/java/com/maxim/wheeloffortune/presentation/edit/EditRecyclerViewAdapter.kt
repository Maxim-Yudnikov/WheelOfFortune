package com.maxim.wheeloffortune.presentation.edit

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.maxim.wheeloffortune.R
import com.maxim.wheeloffortune.presentation.SimpleTextWatcher
import com.maxim.wheeloffortune.presentation.main.UiItem

class EditRecyclerViewAdapter(
    private val communication: ListEditCommunication,
    private val listener: Listener
) : RecyclerView.Adapter<EditRecyclerViewAdapter.ViewHolder>() {
    private val textWatchers = mutableListOf<Pair<EditText, SimpleTextWatcher>>()
    private var lastLength = 0
    fun update() {
        lastLength = textWatchers.size
        textWatchers.forEach {
            it.first.removeTextChangedListener(it.second)
        }
        textWatchers.clear()
        notifyDataSetChanged()
    }

    fun clear() {
        communication.clear()
    }

    class EmptyViewHolder(view: View) : ViewHolder(view)

    abstract class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        open fun bind(
            item: UiItem,
            position: Int,
            textWatchers: MutableList<Pair<EditText, SimpleTextWatcher>>
        ) {
        }

        class Base(view: View, private val listener: Listener, private val setFocus: Boolean) :
            ViewHolder(view) {
            override fun bind(
                item: UiItem,
                position: Int,
                textWatchers: MutableList<Pair<EditText, SimpleTextWatcher>>
            ) {
                val nameTextView = itemView.findViewById<EditText>(R.id.nameEditText)
                item.showText(nameTextView)
                val textWatcher = object : SimpleTextWatcher() {
                    override fun afterTextChanged(s: Editable?) {
                        listener.onTextChanged(position, s.toString())
                    }
                }
                textWatchers.add(Pair(nameTextView, textWatcher))
                nameTextView.addTextChangedListener(textWatcher)
                if (setFocus) {
                    val imm =
                        nameTextView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    nameTextView.requestFocus()
                    imm.toggleSoftInput(0, 0)
                    imm.showSoftInput(nameTextView, 0)
                }

                val deleteImageButton = itemView.findViewById<ImageButton>(R.id.deleteItem)
                deleteImageButton.setOnClickListener {
                    listener.delete(position)
                }

                val changeColorButton = itemView.findViewById<ImageButton>(R.id.changeColorButton)
                changeColorButton.setBackgroundResource(item.getData().second)
                changeColorButton.setOnClickListener {
                    val color = item.changeColor(position, listener)
                    changeColorButton.setBackgroundResource(getColorResourceId(color))
                }
            }

            //todo fix below
            private fun getColorResourceId(id: Int): Int {
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

    override fun getItemViewType(position: Int): Int {
        return if (communication.getList()[position] is UiItem.FailedUiItem) 1
        else if (position == itemCount - 1 && lastLength < itemCount) 2
        else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == 1)
                R.layout.empty_item_list else R.layout.item_item, parent, false
        )
        val setFocus = viewType == 2
        return if (viewType == 1) EmptyViewHolder(view) else ViewHolder.Base(
            view,
            listener,
            setFocus
        )
    }

    override fun getItemCount(): Int {
        return communication.getList().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(communication.getList()[position], position, textWatchers)
    }

    interface Listener {
        fun onTextChanged(id: Int, text: String)
        fun changeColor(id: Int, color: Int)
        fun delete(id: Int)
    }
}