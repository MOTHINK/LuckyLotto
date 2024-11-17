package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.CustomBlue

@Composable
fun SwitchWithCustomIconDemo(isPrivate: (Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(false) }

    SwitchWithCustomIcon(
        isChecked = checkedState.value,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppGreen,
            uncheckedThumbColor = CustomBlue,
            checkedTrackColor = CustomBlue,
            uncheckedTrackColor = Color(0xFFFF3849),
            checkedBorderColor = CustomBlue,
            uncheckedBorderColor = Color(0xFFFF3849)
        ),
        thumbContent = {
            Icon(
                imageVector = if (checkedState.value) {
                    Icons.Filled.Check
                } else {
                    Icons.Filled.Close
                },
                tint = Color.White,
                contentDescription = "Thumb Icon",
                modifier = Modifier.size(20.dp)
            )
        },
        onCheckedChange = {
            checkedState.value = it
            isPrivate(it)
        }
    )
}