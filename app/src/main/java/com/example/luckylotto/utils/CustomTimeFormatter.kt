package com.example.luckylotto.utils

class CustomTimeFormatter {
    companion object {
        fun formatMillisToTime(millis: Long): String {
            val hours = millis / 3_600_000
            val minutes = (millis % 3_600_000) / 60_000
            val seconds = (millis % 60_000) / 1000

            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
}