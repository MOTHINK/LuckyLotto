package com.example.luckylotto.utils

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

fun randomTicketNumbers(): String {
    return String.format("%06d", (0..999999).random())
}