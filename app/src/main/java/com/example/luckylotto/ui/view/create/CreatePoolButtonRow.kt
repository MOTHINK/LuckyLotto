package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun CreatePoolButtonRow(mainViewModel: MainViewModel, maxTickets: Int, closeTime: Long, poolImage: String, isPrivate: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CreateNewPoolButton(mainViewModel,maxTickets,closeTime,poolImage,isPrivate)
        }
    }
}