package com.example.luckylotto.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navDeepLink
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.ui.view.login.LoginScreen
import com.example.luckylotto.ui.view.play.PlayScreen
import com.example.luckylotto.ui.view.profile.ProfileScreen
import com.example.luckylotto.ui.view.create.CreatePoolScreen
import com.example.luckylotto.ui.viewmodel.MainViewModel

class AppNavigation private constructor() {
    private lateinit var navController: NavController
    companion object { val instance:AppNavigation by lazy { AppNavigation() } }
    @SuppressLint("ComposableNaming", "RestrictedApi")
    @Composable
    fun InitializeNavigation(mainViewModel: MainViewModel) {
        navController = rememberNavController()
        HandleNavBarSelection(mainViewModel)
        NavHost(
            this.setNavController(navController) as NavHostController,
            remember(navController) {
                navController.createGraph(startDestination = startDestination()) {
                    composable(NavigationItem.LOGIN.route) { LoginScreen(mainViewModel) }
                    composable(NavigationItem.PROFILE.route) { ProfileScreen(mainViewModel) }
                    composable(NavigationItem.PLAY.route, deepLinks = listOf(navDeepLink { uriPattern = DeepLinkUriPatternItem.PLAY_DEEP_LINK.route })) {
                        PlayScreen(mainViewModel, it.arguments?.getString(DeepLinkUriPatternItem.PLAY_DEEP_LINK_ARGUMENT.route).orEmpty())
                    }
                    composable(NavigationItem.CREATE.route) { CreatePoolScreen(mainViewModel) }
                }
            }
        )
    }

    private fun startDestination(): String {
        return if(FirebaseAuthentication.instance.getFirebaseCurrentUser() != null) NavigationItem.PROFILE.route else NavigationItem.LOGIN.route
    }

    private fun setNavController(navController: NavController): NavController {
        this.navController = navController
        return this.navController
    }

    @SuppressLint("RestrictedApi")
    fun appNavigation() = listOf(
        { this.navController.navigate(NavigationItem.LOGIN.route) },
        { this.navController.navigate(NavigationItem.PROFILE.route) },
        { this.navController.navigate(NavigationItem.PLAY.route) },
        { this.navController.navigate(NavigationItem.CREATE.route) }
    )

    @Composable
    private fun HandleNavBarSelection(mainViewModel: MainViewModel) {
        val destination by this.navController.currentBackStackEntryAsState()
        LaunchedEffect(destination) {
            if (destination?.destination?.route == NavigationItem.PLAY.route) { mainViewModel.setNavBarIndex(0) }
            if (destination?.destination?.route == NavigationItem.CREATE.route) { mainViewModel.setNavBarIndex(1) }
            if (destination?.destination?.route == NavigationItem.PROFILE.route) { mainViewModel.setNavBarIndex(2) }
        }
    }
}