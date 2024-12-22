package com.example.luckylotto.ui.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.model.PrizeRequest
import com.example.luckylotto.data.model.Ticket
import com.example.luckylotto.data.repository.ticket_repository.OnlineTicketRepository
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.CustomBlue
import com.example.luckylotto.ui.theme.CustomRed
import com.example.luckylotto.ui.view.components.CountDownDateTime
import com.example.luckylotto.ui.view.components.PoolCardId
import com.example.luckylotto.ui.view.components.TicketNumbers
import com.example.luckylotto.ui.view.components.TicketsBought
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun TicketInfoDialog(mainViewModel: MainViewModel, onDismissRequest: (Boolean) -> Unit, ticket: Ticket, updateTicket: (String) -> Unit, shareTicket: (String) -> Unit, deleteTicket: (String) -> Unit) {
    var showUpConfirmDeleteTicketAlertDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    if(showUpConfirmDeleteTicketAlertDialog) {
        ConfirmDeleteTicketAlertDialog(
            onDismissRequest = {
                showUpConfirmDeleteTicketAlertDialog = it
                onDismissRequest(false)
            },
            onConfirmation = { deleteTicket(ticket.ticketId) },
            "Are you sure you want delete this ticket?"
        )
    }
    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 10.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ticket.poolImage,
                    contentDescription = "Image from URL",
                    modifier = Modifier.fillMaxWidth().height(400.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxWidth().height(400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(5.dp,0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(modifier = Modifier.weight(0.9f)) {
                            PoolCardId(poolId = ticket.poolId)
                        }
                        Box(modifier = Modifier.weight(0.1f)) {
                            IconButton(
                                onClick = { onDismissRequest(false) },
                                modifier = Modifier.size(30.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = CustomRed,
                                    contentColor = Color.Red
                                )
                            ) {
                                Icon(modifier = Modifier.size(20.dp), imageVector = ImageVector.vectorResource(
                                    R.drawable.close), contentDescription = "Close", tint = Color.White)
                            }
                        }
                    }
                    TicketNumbers(Modifier.size(40.dp),ticket.ticketNumber, ticket.winningNumber)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )  {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.group), contentDescription = "Number of players", tint = Color.Black)
                        TicketsBought(ticket.ticketsBought.toString(),ticket.maxTickets.toString())
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )  {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.timer), contentDescription = "Time", tint = Color.Black)
                        CountDownDateTime(ticket.closeTime)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )  {
                        IconButton(
                            onClick = { updateTicket(ticket.poolId) },
                            modifier = Modifier.size(60.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = CustomBlue
                            )
                        ) {
                            Icon(modifier = Modifier.size(35.dp), imageVector = ImageVector.vectorResource(
                                R.drawable.synchronize), contentDescription = "Synchronize", tint = Color.White)
                        }
                        IconButton(
                            onClick = { shareTicket(ticket.ticketId) },
                            modifier = Modifier.size(60.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = CustomBlue
                            )
                        ) {
                            Icon(modifier = Modifier.size(35.dp), imageVector = ImageVector.vectorResource(
                                R.drawable.share), contentDescription = "Share", tint = Color.White)
                        }
                        IconButton(
                            onClick = {
                                  showUpConfirmDeleteTicketAlertDialog = true
                            },
                            modifier = Modifier.size(60.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = CustomRed
                            )
                        ) {
                            Icon(modifier = Modifier.size(35.dp), imageVector = ImageVector.vectorResource(
                                R.drawable.trash), contentDescription = "delete", tint = Color.White)
                        }
                    }
                    if(ticket.ticketNumber == ticket.winningNumber) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        )  {
                            Button(
                                modifier = Modifier.padding(20.dp, 0.dp).fillMaxWidth().height(50.dp),
                                onClick = {
                                    coroutineScope.launch {
                                        if(
                                            this.async {
                                                mainViewModel.prizeRequest(PrizeRequest(
                                                    userId = FirebaseAuthentication.instance.getFirebaseCurrentUser()!!.uid,
                                                    ticketId = ticket.ticketId,
                                                    poolId = ticket.poolId
                                                ))
                                            }.await()
                                        ) {
                                            OnlineTicketRepository.instance.updateTicket(mainViewModel.firebaseDB,ticket)
                                        }
                                    }
                                },
                                shape = ShapeDefaults.Small,
                                colors = ButtonColors(AppGreen, AppGreen, AppGreen, AppGreen)
                            ) {
                                Row {
                                    Text(
                                        text = "claim your prize",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}