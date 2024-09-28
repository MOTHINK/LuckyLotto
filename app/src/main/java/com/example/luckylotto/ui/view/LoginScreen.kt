package com.example.luckylotto.ui.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.luckylotto.R
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.view.components.GoogleAuthenticationButton
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun LoginScreen(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box {
                LoginImage()
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box {
                GoogleAuthenticationButton(mainViewModel)
            }

        }
    }
}

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
