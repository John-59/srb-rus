package com.trainer.srb.rus.feature.admin.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToPredefinedDictionaryEditor() {
    this.navigate(PredefinedDictionaryEditorDestination.route)
}