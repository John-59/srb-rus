package com.trainer.srb.rus.feature.addword.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.feature.addword.AddWordArgs

fun NavHostController.navigateToAddWord(
    translation: Translation<Word.Serbian, Word.Russian>
) {
    val srbLatValue = translation.source.latinValue
    val srbCyrValue = translation.source.cyrillicValue
    val rusValues = translation.translations.map {
        it.value
    }.joinToString(AddWordArgs.rusValuesSeparator)
    val route = "${AddWordDestination.route}?$srbLatValue?$srbCyrValue?$rusValues"
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