package com.example.luckylotto.ui.navigation

enum class Routes(val route: String) {
    LOGIN("login"),
    PROFILE("profile"),
    PLAY("play"),
    CREATE("create");
    companion object {

    }
}

sealed class NavigationItem(val route: String) {
    data object LOGIN: NavigationItem(Routes.LOGIN.route)
    data object PROFILE: NavigationItem(Routes.PROFILE.route)
    data object PLAY: NavigationItem(Routes.PLAY.route)
    data object CREATE: NavigationItem(Routes.CREATE.route)
}