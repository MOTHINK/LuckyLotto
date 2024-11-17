package com.example.luckylotto.ui.view.create

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SwitchWithCustomIcon(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    thumbContent: (@Composable () -> Unit)? = null,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    Switch(
        modifier = modifier,
        checked = isChecked,
        enabled = enabled,
        colors = colors,
        thumbContent = thumbContent,
        onCheckedChange = onCheckedChange
    )
}