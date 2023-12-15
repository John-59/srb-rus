package com.trainer.srb.rus.feature.dictionary.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToDictionary() {
    this.navigate(DictionaryDestination.route)
}