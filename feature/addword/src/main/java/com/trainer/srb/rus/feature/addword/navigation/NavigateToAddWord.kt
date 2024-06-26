package com.trainer.srb.rus.feature.addword.navigation

import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.feature.addword.AddWordArgs

fun NavHostController.navigateToAddWord(
    translation: Translation<Word.Serbian, Word.Russian>
) {
    val srbLatValue = Uri.encode(translation.source.latinValue)
    val srbCyrValue = Uri.encode(translation.source.cyrillicValue)
    val rusValues = Uri.encode(
        translation.translations.map {
            it.value
        }.joinToString(AddWordArgs.rusValuesSeparator)
    )
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