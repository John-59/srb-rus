package com.trainer.srb.rus

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(): ViewModel() {

    val destinations = MainScreenDestination.Inner.entries

    val startDestination = MainScreenDestination.Inner.LEARN

    fun getDestination(route: String): MainScreenDestination.Inner? {
        return MainScreenDestination.Inner.entries.firstOrNull {
            it.route == route
        }
    }
}