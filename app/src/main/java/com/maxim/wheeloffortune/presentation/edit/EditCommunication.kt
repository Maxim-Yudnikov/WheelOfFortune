package com.maxim.wheeloffortune.presentation.edit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.maxim.wheeloffortune.presentation.main.UiItem

interface EditCommunication : StateEditCommunication, ListEditCommunication

interface StateEditCommunication {
    fun showState(state: EditState)
    fun observeState(owner: LifecycleOwner, observer: Observer<EditState>)
}

interface ListEditCommunication {
    fun showList(list: List<UiItem>)
    fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>)
    fun getList(): List<UiItem>
    fun clear()
}