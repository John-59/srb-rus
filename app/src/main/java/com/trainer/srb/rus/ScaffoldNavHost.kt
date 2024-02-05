//package com.trainer.srb.rus
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.trainer.srb.rus.editword.navigation.navigateToEditWord
//import com.trainer.srb.rus.feature.addword.navigation.navigateToAddWord
//import com.trainer.srb.rus.feature.home.HomeScreen
//import com.trainer.srb.rus.feature.home.navigation.HomeScreenDestination
//import com.trainer.srb.rus.feature.learn.navigation.navigateToLearn
//import com.trainer.srb.rus.feature.search.SearchScreen
//import com.trainer.srb.rus.feature.search.navigation.SearchScreenDestination
//import com.trainer.srb.rus.feature.search.navigation.navigateToSearch
//
//@Composable
//fun ScaffoldNavHost(
//    navController: NavHostController,
//) {
//    NavHost(
//        navController = navController,
//        startDestination = HomeScreenDestination.route
//    ) {
//        composable(HomeScreenDestination.route) {
//            HomeScreen(
//                navigateToSearch = {
//                    navController.navigateToSearch()//HomeScreenDestination.route)
//                },
//                navigateToLearn = {
//                    navController.navigateToLearn(it, HomeScreenDestination.route)
//                }
//            )
//        }
//        composable(SearchScreenDestination.route) {
//            SearchScreen(
//                navigateToAddWord = {
//                    navController.navigateToAddWord(it, SearchScreenDestination.route)
//                },
//                navigateToEditWord = {
//                    navController.navigateToEditWord(it, SearchScreenDestination.route)
//                }
//            )
//        }
//    }
//}