package com.maxim.wheeloffortune.presentation.edit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.maxim.wheeloffortune.presentation.main.UiItem

interface EditCommunication {
    fun showList(list: List<UiItem.BaseUiItem>)
    fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem.BaseUiItem>>)
    fun getList(): List<UiItem.BaseUiItem>
    fun getDiffResult(): DiffUtil.DiffResult
    fun clear()
}