package com.maxim.wheeloffortune.presentation.edit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.maxim.wheeloffortune.presentation.main.UiItem

class BaseEditCommunication : EditCommunication {
    private val listLiveData = MutableLiveData<List<UiItem>>()
    private val stateLiveData = MutableLiveData<EditState>()
    override fun showState(state: EditState) {
        stateLiveData.value = state
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<EditState>) {
        stateLiveData.observe(owner, observer)
    }

    override fun showList(list: List<UiItem>) {
        listLiveData.value = list
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
        listLiveData.observe(owner, observer)
    }

    override fun getList(): List<UiItem> {
        return listLiveData.value ?: emptyList()
    }
    override fun clear() {
        listLiveData.value = emptyList()
    }
}