package com.trainer.srb.rus.feature.editword.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

fun NavHostController.navigateToEditWord(
    translation: Translation<Word.Serbian, Word.Russian>,
) {
    val route = "${EditWordDestination.route}/${translation.source.latinId}"
    this.navigate(route) {
        popUpTo(
            graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}