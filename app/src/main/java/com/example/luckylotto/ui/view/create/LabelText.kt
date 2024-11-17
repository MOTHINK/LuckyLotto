package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.theme.CustomBlue


@Composable
fun LabelText(text: String) {
    Text(
        modifier = Modifier.padding(20.dp).background(CustomBlue, shape = RoundedCornerShape(5.dp)),
        text = " $text ",
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}