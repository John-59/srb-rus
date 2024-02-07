package com.trainer.srb.rus

import androidx.compose.ui.graphics.vector.ImageVector
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.feature.dictionary.navigation.SearchScreenDestination
import com.trainer.srb.rus.feature.learn.navigation.LearnScreenDestination

object MainScreenDestination {
    const val route = "main"

    enum class Inner(
        val route: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val text: String,
    ) {
        LEARN(
            route = LearnScreenDestination.route,
            selectedIcon = SrIcons.Learn,
            unselectedIcon = SrIcons.LearnBorder,
            text = "Упражнения"
        ),
        DICTIONARY(
            route = SearchScreenDestination.route,
            selectedIcon = SrIcons.Dictionary,
            unselectedIcon = SrIcons.DictionaryBorder,
            text = "Словарь"
        ),
        STATISTICS(
            route = "",
            selectedIcon = SrIcons.Statistics,
            unselectedIcon = SrIcons.StatisticsBorder,
            text = "Статистика"
        )
    }
}