package com.trainer.srb.rus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.trainer.srb.rus.ui.theme.SrbrusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SrbrusTheme {
                val navController = rememberNavController()
                MainNavHost(
                    navController = navController,
                )
            }
        }
    }
}