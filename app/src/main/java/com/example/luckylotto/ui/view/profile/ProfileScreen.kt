package com.example.luckylotto.ui.view.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun ProfileScreen(mainViewModel: MainViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            ProfileCard(mainViewModel,Modifier)
            Spacer(modifier = Modifier.height(10.dp))
            TicketCardList(mainViewModel)
        }
    }
}

@Composable
fun TicketCardList(mainViewModel: MainViewModel) {
    val tickets by mainViewModel.tickets.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tickets.size) { TicketCard(tickets[it], mainViewModel) }
    }
}

