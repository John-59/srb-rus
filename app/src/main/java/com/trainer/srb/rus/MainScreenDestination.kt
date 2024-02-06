package com.trainer.srb.rus

import androidx.compose.ui.graphics.vector.ImageVector
import com.trainer.srb.rus.core.design.SrIcons

//object MainScreenDestination {
//    const val route = "main"
//}

enum class MainScreenDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val text: String,
) {
    LEARN(
        selectedIcon = SrIcons.Learn,
        unselectedIcon = SrIcons.LearnBorder,
        text = "Упражнения"
    ),
    DICTIONARY(
        selectedIcon = SrIcons.Dictionary,
        unselectedIcon = SrIcons.DictionaryBorder,
        text = "Словарь"
    ),
    STATISTICS(
        selectedIcon = SrIcons.Statistics,
        unselectedIcon = SrIcons.StatisticsBorder,
        text = "Статистика"
    )
}