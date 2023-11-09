package com.maxim.wheeloffortune.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxim.wheeloffortune.domain.edit.EditInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(
    private val interactor: EditInteractor,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    fun deleteWheel() {
        viewModelScope.launch(dispatcher) {
            interactor.deleteWheel()
        }
    }

    fun createItem() {
        interactor.createItem()
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch(dispatcher) {
            interactor.deleteItem(id)
        }
    }

    fun changeItemName(id: Int, title: String) {
        interactor.changeItemName(id, title)
    }

    fun changeItemColor(id: Int, color: Int) {
        interactor.changeItemColor(id, color)
    }

    fun endEditing(title: String) {
        viewModelScope.launch(dispatcher) {
            interactor.endEditing(title)
        }
    }
}