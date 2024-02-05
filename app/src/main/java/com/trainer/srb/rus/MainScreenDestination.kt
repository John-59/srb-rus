package com.trainer.srb.rus

import androidx.compose.ui.graphics.vector.ImageVector
import com.trainer.srb.rus.core.design.SrbRusIcons

//object MainScreenDestination {
//    const val route = "main"
//}

enum class MainScreenDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val text: String,
) {
    LEARN(
        selectedIcon = SrbRusIcons.Learn,
        unselectedIcon = SrbRusIcons.LearnBorder,
        text = "Упражнения"
    ),
    DICTIONARY(
        selectedIcon = SrbRusIcons.Dictionary,
        unselectedIcon = SrbRusIcons.DictionaryBorder,
        text = "Словарь"
    ),
    STATISTICS(
        selectedIcon = SrbRusIcons.Statistics,
        unselectedIcon = SrbRusIcons.StatisticsBorder,
        text = "Статистика"
    )
}