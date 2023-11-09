package com.maxim.wheeloffortune.presentation.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil

interface Communication: ListCommunication {
    fun showState(state: State)
    fun observeState(owner: LifecycleOwner, observer: Observer<State>)
}

interface ListCommunication {
    fun showList(list: List<UiItem>)
    fun getList(): List<UiItem>
    fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>)
    fun getDiffResult(): DiffUtil.DiffResult
}