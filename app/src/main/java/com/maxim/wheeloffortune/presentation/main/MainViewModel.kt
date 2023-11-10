package com.maxim.wheeloffortune.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxim.wheeloffortune.domain.main.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: Interactor,
    private val communication: Communication,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {
    fun getItemList() {
        viewModelScope.launch(dispatcher) {
            communication.showList(interactor.getItemList().map { it.mapToUi() })
        }
    }

    fun openItem(id: Int) {
        viewModelScope.launch(dispatcher) {
            interactor.openItem(id)
        }
    }

    fun rotate() {
        communication.showState(State.Rotating)
        viewModelScope.launch(dispatcher) {
            communication.showState(State.Done(interactor.rotate()))
        }
    }
}