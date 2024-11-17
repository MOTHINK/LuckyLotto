package com.example.luckylotto.ui.view.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckylotto.R
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun TopNavBarSearchPoolCard(mainViewModel: MainViewModel) {
    val text by mainViewModel.poolSearchText.collectAsState()
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = AppGreen,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.padding(10.dp),
                            painter = painterResource(id = R.drawable.lupa),
                            contentDescription = "Magnifying glasses"
                        )
                    },
                    value = text,
                    onValueChange = {
                        mainViewModel.setPoolSearchText(it)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = "Search lottery by ID",
                            textAlign = TextAlign.Start,
                            fontSize = 15.sp
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )
            }
        }
    }
}