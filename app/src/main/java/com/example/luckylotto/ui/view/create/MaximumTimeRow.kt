package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun MaximumTimeRow(mainViewModel: MainViewModel, maxTimeValue: (Long) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                LabelText("Maximum Time")
            }
            CustomSlider(mainViewModel.maxTimeValues,"Hours") { maxTimeValue(3600000L*it) }
        }
    }
}