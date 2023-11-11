package com.maxim.wheeloffortune.presentation.edit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.maxim.wheeloffortune.presentation.main.DiffUtilCallback
import com.maxim.wheeloffortune.presentation.main.RecyclerViewAdapter
import com.maxim.wheeloffortune.presentation.main.UiItem

class BaseEditCommunication : EditCommunication {
    private val liveData = MutableLiveData<List<UiItem>>()
    override fun showList(list: List<UiItem>) {
        liveData.value = list
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
        liveData.observe(owner, observer)
    }

    override fun getList(): List<UiItem> {
        return liveData.value ?: emptyList()
    }
    override fun clear() {
        liveData.value = emptyList()
    }
}