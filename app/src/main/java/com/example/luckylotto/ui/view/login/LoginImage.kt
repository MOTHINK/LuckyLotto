package com.example.luckylotto.ui.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.luckylotto.R

@Composable
fun LoginImage() {
    val imageModifier = Modifier
        .size(250.dp)
        .background(Color.Transparent)
    Image(
        painter = painterResource(id = R.drawable.login_image),
        contentDescription = "Login Image",
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )
}