package com.example.luckylotto.ui.view.login


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun LoginScreen(mainViewModel: MainViewModel) {
    Box(modifier = Modifier.fillMaxSize().background(AppGreen)) {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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
