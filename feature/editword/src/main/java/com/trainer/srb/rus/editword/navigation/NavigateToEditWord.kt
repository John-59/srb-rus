package com.trainer.srb.rus.editword.navigation

import androidx.navigation.NavHostController
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

fun NavHostController.navigateToEditWord(
    translation: Translation<Word.Serbian, Word.Russian>,
    previousDestination: String
) {
    val route = "${EditWordDestination.route}/${translation.source.latinId}"
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(
            previousDestination
        )
    }
}