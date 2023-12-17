package com.trainer.srb.rus.feature.search.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToSearch() {
    this.navigate(SearchScreenDestination.route)
}