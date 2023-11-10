package com.maxim.wheeloffortune.presentation.edit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.maxim.wheeloffortune.presentation.main.DiffUtilCallback
import com.maxim.wheeloffortune.presentation.main.RecyclerViewAdapter
import com.maxim.wheeloffortune.presentation.main.UiItem

class BaseEditCommunication : EditCommunication {
    private val liveData = MutableLiveData<List<UiItem.BaseUiItem>>()
    private lateinit var diffResult: DiffUtil.DiffResult
    override fun showList(list: List<UiItem.BaseUiItem>) {
        val callback = DiffUtilCallback(liveData.value ?: emptyList(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        liveData.value = list
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem.BaseUiItem>>) {
        liveData.observe(owner, observer)
    }

    override fun getList(): List<UiItem.BaseUiItem> {
        return liveData.value ?: emptyList()
    }

    override fun getDiffResult(): DiffUtil.DiffResult = diffResult
    override fun clear() {
        liveData.value = emptyList()
    }
}