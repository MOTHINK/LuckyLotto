package com.example.luckylotto.ui.navigation

enum class DeepLinkUriPattern(val route: String) {
    PLAY_DEEP_LINK("https://luckylotto.dev/play/{poolId}"),
    PLAY_DEEP_LINK_ARGUMENT("poolId")
}
sealed class DeepLinkUriPatternItem(val route: String) {
    data object PLAY_DEEP_LINK: DeepLinkUriPatternItem(DeepLinkUriPattern.PLAY_DEEP_LINK.route)
    data object PLAY_DEEP_LINK_ARGUMENT: DeepLinkUriPatternItem(DeepLinkUriPattern.PLAY_DEEP_LINK_ARGUMENT.route)
}