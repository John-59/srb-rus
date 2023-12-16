package com.trainer.srb.rus

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.trainer.srb.rus.feature.actions.ActionsScreen
import com.trainer.srb.rus.feature.actions.navigation.ActionsDestination
import com.trainer.srb.rus.feature.addword.AddWordScreen
import com.trainer.srb.rus.feature.addword.navigation.AddWordDestination
import com.trainer.srb.rus.feature.addword.navigation.navigateToAddWord
import com.trainer.srb.rus.feature.admin.AddRusSrbTranslationScreen
import com.trainer.srb.rus.feature.admin.AdminActionsScreen
import com.trainer.srb.rus.feature.admin.navigation.AddSrbRusTranslationDestination
import com.trainer.srb.rus.feature.admin.AddSrbRusTranslationScreen
import com.trainer.srb.rus.feature.admin.PredefinedDictionaryEditorScreen
import com.trainer.srb.rus.feature.admin.navigation.AddRusSrbTranslationDestination
import com.trainer.srb.rus.feature.admin.navigation.AdminActionsDestination
import com.trainer.srb.rus.feature.admin.navigation.PredefinedDictionaryEditorDestination
import com.trainer.srb.rus.feature.admin.navigation.navigateToAddRusSrbTranslation
import com.trainer.srb.rus.feature.admin.navigation.navigateToAddSrbRusTranslation
import com.trainer.srb.rus.feature.admin.navigation.navigateToAdminActions
import com.trainer.srb.rus.feature.admin.navigation.navigateToPredefinedDictionaryEditor
import com.trainer.srb.rus.feature.home.HomeScreen
import com.trainer.srb.rus.feature.home.navigation.HomeScreenDestination

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route
    ) {
        composable(HomeScreenDestination.route) {
            HomeScreen()
        }

        composable(ActionsDestination.route) {
            ActionsScreen(
                navigateToAddWord = navController::navigateToAddWord,
                navigateToAdmin = navController::navigateToAdminActions
            )
        }
        composable(AddWordDestination.route) {
            AddWordScreen()
        }
        composable(AdminActionsDestination.route) {
            AdminActionsScreen(
                navigateToAddSrbRusTranslation = navController::navigateToAddSrbRusTranslation,
                navigateToAddRusSrbTranslation = navController::navigateToAddRusSrbTranslation,
                navigateToPredefinedDictionaryEditor = navController::navigateToPredefinedDictionaryEditor
            )
        }
        composable(AddSrbRusTranslationDestination.route) {
            AddSrbRusTranslationScreen()
        }
        composable(AddRusSrbTranslationDestination.route) {
            AddRusSrbTranslationScreen()
        }
        composable(PredefinedDictionaryEditorDestination.route) {
            PredefinedDictionaryEditorScreen()
        }
    }
}