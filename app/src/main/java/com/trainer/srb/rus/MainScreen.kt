package com.trainer.srb.rus

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.trainer.srb.rus.core.exercise.ExerciseType
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.feature.about.navigation.navigateToAbout
import com.trainer.srb.rus.feature.dictionary.navigation.navigateToSearch
import com.trainer.srb.rus.feature.learn.navigation.navigateToLearn

@Composable
fun MainScreen(
    navigateToExercise: (ExerciseType) -> Unit,
    navigateToAddWord: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    navigateToEditWord: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()//.value?.destination?.route
    val currentDestination by remember {
        derivedStateOf {
            if (currentBackStackEntry == null) {
                viewModel.startDestination
            } else {
                viewModel.getDestination(currentBackStackEntry?.destination?.route.orEmpty())
            }
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavBar(
                destinations = viewModel.destinations,
                currentDestination = currentDestination,
                onDestinationChanged = {
                    when (it) {
                        MainScreenDestination.Inner.LEARN -> navController.navigateToLearn()
                        MainScreenDestination.Inner.DICTIONARY -> navController.navigateToSearch()
                        MainScreenDestination.Inner.ABOUT -> navController.navigateToAbout()
                    }
                }
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding)
        ) {
            MainScreenNavHost(
                navController = navController,
                navigateToExercise = navigateToExercise,
                navigateToAddWord = navigateToAddWord,
                navigateToEditWord = navigateToEditWord,
                startDestination = viewModel.startDestination
            )
        }
    }
}