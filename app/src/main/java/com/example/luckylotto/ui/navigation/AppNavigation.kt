package com.example.luckylotto.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.luckylotto.ui.view.LoginScreen
import com.example.luckylotto.ui.view.PlayScreen
import com.example.luckylotto.ui.view.ProfileScreen
import com.example.luckylotto.ui.view.components.CreatePoolScreen
import com.example.luckylotto.ui.viewmodel.MainViewModel

class AppNavigation private constructor() {
    private lateinit var navController: NavController
    companion object {
        val instance:AppNavigation by lazy { AppNavigation() }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun InitializeNavigation(navController: NavController, mainViewModel: MainViewModel) {
        NavHost(
            this.setNavController(navController) as NavHostController,
            remember(navController) {
                navController.createGraph(startDestination = NavigationItem.LOGIN.route) {
                    composable(NavigationItem.LOGIN.route) { LoginScreen(mainViewModel) }
                    composable(NavigationItem.PROFILE.route) { ProfileScreen(mainViewModel) }
                    composable(NavigationItem.PLAY.route) { PlayScreen(mainViewModel) }
                    composable(NavigationItem.CREATE.route) { CreatePoolScreen(mainViewModel) }
                }
            }
        )
    }

    private fun setNavController(navController: NavController): NavController {
        this.navController = navController
        return this.navController
    }

    fun appNavigation() = listOf(
        {this.navController.navigate(NavigationItem.LOGIN.route)},
        {this.navController.navigate(NavigationItem.PROFILE.route)},
        {this.navController.navigate(NavigationItem.PLAY.route)},
        {this.navController.navigate(NavigationItem.CREATE.route)}
    )

}