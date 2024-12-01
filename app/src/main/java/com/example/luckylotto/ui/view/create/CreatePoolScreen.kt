package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun CreatePoolScreen(mainViewModel: MainViewModel) {
    var maxTickets by remember { mutableIntStateOf(mainViewModel.maxTicketValues[0]) }
    var closeTime by remember { mutableLongStateOf(3600000L*mainViewModel.maxTimeValues[0]) }
    var poolImage by remember { mutableStateOf(mainViewModel.imageList[0]) }
    var isPrivate by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White).padding(10.dp)) {
        Box(modifier = Modifier.fillMaxSize().background(color = AppGreen, shape = RoundedCornerShape(10.dp))) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    TitleRow()
                    Spacer(modifier = Modifier.height(10.dp))
                    MaximumTicketsRow(mainViewModel) { value -> maxTickets = value }
                    Spacer(modifier = Modifier.height(10.dp))
                    MaximumTimeRow(mainViewModel) { value -> closeTime = value }
                    Spacer(modifier = Modifier.height(10.dp))
                    ImagePickerRow(mainViewModel) { value -> poolImage = value }
                    Spacer(modifier = Modifier.height(10.dp))
                    PrivateOption() { isPrivate = it }
                    Spacer(modifier = Modifier.height(10.dp))
                    CreatePoolButtonRow(mainViewModel,maxTickets,closeTime,poolImage,isPrivate)
                }
            }
        }
    }
}