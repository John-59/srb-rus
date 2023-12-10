package com.trainer.srb.rus.feature.addword.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToAddWord() {
    this.navigate(AddWordDestination.route)
}