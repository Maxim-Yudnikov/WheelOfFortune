package com.maxim.wheeloffortune

import android.app.Application
import com.maxim.wheeloffortune.data.BaseWheelCache
import com.maxim.wheeloffortune.data.BaseWheelDataSource
import com.maxim.wheeloffortune.data.BaseWheelItemsCache
import com.maxim.wheeloffortune.data.room.DatabaseProvider
import com.maxim.wheeloffortune.domain.BaseFailureHandler
import com.maxim.wheeloffortune.domain.edit.BaseEditInteractor
import com.maxim.wheeloffortune.domain.main.BaseInteractor
import com.maxim.wheeloffortune.presentation.edit.BaseEditCommunication
import com.maxim.wheeloffortune.presentation.edit.BaseTitleUiValidator
import com.maxim.wheeloffortune.presentation.edit.EditCommunication
import com.maxim.wheeloffortune.presentation.edit.EditViewModel
import com.maxim.wheeloffortune.presentation.main.BaseCommunication
import com.maxim.wheeloffortune.presentation.main.Communication
import com.maxim.wheeloffortune.presentation.main.MainViewModel

class App : Application() {
    lateinit var communication: Communication
    lateinit var viewModel: MainViewModel
    lateinit var editCommunication: EditCommunication
    lateinit var editViewModel: EditViewModel

    override fun onCreate() {
        super.onCreate()
        communication = BaseCommunication()
        val dataSource = BaseWheelDataSource(
            DatabaseProvider(this).provideDatabase().dao(),
            BaseWheelCache(),
            BaseWheelItemsCache()
        )
        viewModel = MainViewModel(BaseInteractor(dataSource), communication)

        editCommunication = BaseEditCommunication()
        editViewModel = EditViewModel(
            BaseEditInteractor(dataSource, BaseFailureHandler()),
            editCommunication,
            BaseTitleUiValidator(3)
        )
    }
}