package com.trainer.srb.rus.feature.addword.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import com.trainer.srb.rus.core.dictionary.Word

fun NavHostController.navigateToAddWord(word: Word) {
    val (srbLatValue, srbCyrValue, rusValue) = when (word) {
        is Word.Russian -> {
            Triple("", "", Uri.encode(word.value))
        }
        is Word.Serbian -> {
            Triple(Uri.encode(word.value), Uri.encode(word.cyrillicValue), "")
        }
    }
    val route = "${AddWordDestination.route}?$srbLatValue?$srbCyrValue?$rusValue"
    this.navigate(route)
}