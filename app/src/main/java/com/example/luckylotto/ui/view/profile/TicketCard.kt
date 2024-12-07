package com.example.luckylotto.ui.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.luckylotto.data.model.Ticket
import com.example.luckylotto.ui.view.components.CountDownDateTime
import com.example.luckylotto.ui.view.components.PoolCardId
import com.example.luckylotto.ui.view.components.TicketNumbers
import com.example.luckylotto.ui.view.components.TicketsBought
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun TicketCard(ticket: Ticket, mainViewModel: MainViewModel) {
    var showUpTicketInfoDialog by remember { mutableStateOf(false) }

    if(showUpTicketInfoDialog) {
        TicketInfoDialog(
            onDismissRequest = { showUpTicketInfoDialog = it },
            ticket = ticket,
            updateTicket = {},
            shareTicket = {},
            deleteTicket = { mainViewModel.deleteTicketById(ticketId = it) }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                showUpTicketInfoDialog = !showUpTicketInfoDialog
            }
    ) {
        AsyncImage(
            model = ticket.poolImage,
            contentDescription = "Image from URL",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                PoolCardId(poolId = ticket.poolId)
                TicketNumbers(
                    ticket.ticketNumber,
                    Modifier
                        .size(50.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(50.dp)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TicketsBought(ticket.ticketsBought.toString(),ticket.maxTickets.toString())
                    CountDownDateTime(ticket.closeTime) // close time
                }
            }
        }
    }
}