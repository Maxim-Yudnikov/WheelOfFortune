package com.maxim.wheeloffortune.presentation.edit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxim.wheeloffortune.domain.edit.EditInteractor
import com.maxim.wheeloffortune.presentation.main.UiItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(
    private val interactor: EditInteractor,
    private val communication: EditCommunication,
    private val titleValidator: UiValidator,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    fun deleteWheel() {
        viewModelScope.launch(dispatcher) {
            interactor.deleteWheel()
        }
    }

    fun createItem() {
        interactor.createItem()
        communication.showList(interactor.getList().map { it.mapToUi() as UiItem.BaseUiItem })
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch(dispatcher) {
            interactor.deleteItem(id)
            communication.showList(interactor.getList().map { it.mapToUi() })
        }
    }

    fun getItemList() {
        communication.showList(interactor.getList().map { it.mapToUi() })
    }

    fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
        communication.observeList(owner, observer)
    }

    fun changeItemName(id: Int, title: String) {
        interactor.changeItemName(id, title)
    }

    fun changeItemColor(id: Int, color: Int) {
        interactor.changeItemColor(id, color)
    }

    fun endEditing(title: String, onEnd: () -> Unit) {
        if (titleValidator.isValid(title))
        viewModelScope.launch(dispatcher) {
            interactor.endEditing(title)
            onEnd.invoke()
        } else {
            communication.showState(EditState.TitleError(titleValidator.getMessage()))
        }
    }

    fun observeState(owner: LifecycleOwner, observer: Observer<EditState>) {
        communication.observeState(owner, observer)
    }

    fun cancelEditing() {
        interactor.cancelEditing()
    }
}