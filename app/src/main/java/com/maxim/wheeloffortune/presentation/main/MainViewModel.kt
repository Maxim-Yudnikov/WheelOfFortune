package com.maxim.wheeloffortune.presentation.main

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluehomestudio.luckywheel.LuckyWheel
import com.maxim.wheeloffortune.domain.main.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: Interactor,
    private val communication: Communication,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
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

    fun closeItem() {
        interactor.closeItem()
    }

    fun rotate(wheel: LuckyWheel, itemList: List<UiItem>) {
        communication.showState(State.Rotating)

        val random = itemList.indices.random()
        val randomItem = itemList[random]

        wheel.setLuckyWheelReachTheTarget {
            communication.showState(State.Done(randomItem.getData().first))
        }

        wheel.rotateWheelTo(random+1)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        communication.observeState(owner, observer)
    }

    fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
        communication.observeList(owner, observer)
    }
}