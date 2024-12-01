package com.example.luckylotto.ui.view.create

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.viewmodel.MainViewModel


@Composable
fun CreatingPoolSnackBarMessage(mainViewModel: MainViewModel) {
    val snackBarHostState = remember { SnackbarHostState() }
    val message by mainViewModel.snackBarMessage.collectAsState()

    LaunchedEffect(key1 = message) {
        if (message.isNotEmpty()) {
            snackBarHostState.showSnackbar(message)
            mainViewModel.setSnackBarMessage("")
        }
    }

    SnackbarHost(hostState = snackBarHostState) {
        Box(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                modifier = Modifier
                    .background(
                        color = AppGreen,
                        shape = ShapeDefaults.Small
                    )
                    .padding(10.dp),
                text = message,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}