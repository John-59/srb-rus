package com.trainer.srb.rus.feature.admin.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToAdminActions() {
    this.navigate(AdminActionsDestination.route)
}