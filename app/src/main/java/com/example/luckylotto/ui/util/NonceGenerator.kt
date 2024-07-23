package com.example.luckylotto.ui.util

fun generateNonce(length: Int = 32): String {
    val unixTimestamp: Long = 1657514400 // Example Unix timestamp (in seconds)
    val milliseconds = unixTimestamp * 1000L
    return milliseconds.toString()
}