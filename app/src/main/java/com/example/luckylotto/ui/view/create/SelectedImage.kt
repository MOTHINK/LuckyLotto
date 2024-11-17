package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.luckylotto.ui.theme.CustomBlue

@Composable
fun SelectedImage(poolImage: String, position: Int, index: Int, indexChange: (Int) -> Unit, selectedPoolImage: (Int) -> Unit) {
    var poolImageBorderColor by remember { mutableStateOf(Color.Transparent) }

    LaunchedEffect(key1 = index) {
        poolImageBorderColor = if (position == index) {
            CustomBlue
        } else {
            Color.Transparent
        }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp)
            .clip(CircleShape)
            .padding(5.dp)
            .border(
                width = 5.dp,
                color = poolImageBorderColor,
                shape = CircleShape
            )
            .clickable {
                indexChange(position)
                selectedPoolImage(position)
            }
    ) {
        AsyncImage(
            model = poolImage,
            contentDescription = "Image from URL",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}