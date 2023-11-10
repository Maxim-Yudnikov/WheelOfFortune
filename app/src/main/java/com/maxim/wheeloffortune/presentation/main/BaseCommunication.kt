package com.maxim.wheeloffortune.presentation.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.maxim.wheeloffortune.domain.main.DomainItem

class BaseCommunication : Communication {
    private val livedata = MutableLiveData<State>()
    private val listLivedata = MutableLiveData<List<UiItem>>()
    private lateinit var diffResult: DiffUtil.DiffResult
    override fun showState(state: State) {
        livedata.value = state
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<State>) {
        livedata.observe(owner, observer)
    }

    override fun showList(list: List<UiItem>) {
        val callback = DiffUtilCallback(listLivedata.value ?: listOf(UiItem.Empty), list)
        diffResult = DiffUtil.calculateDiff(callback)
        listLivedata.value = list
    }

    override fun getList(): List<UiItem> {
        return listLivedata.value ?: listOf(UiItem.Empty)
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
        listLivedata.observe(owner, observer)
    }

    override fun getDiffResult(): DiffUtil.DiffResult = diffResult
}