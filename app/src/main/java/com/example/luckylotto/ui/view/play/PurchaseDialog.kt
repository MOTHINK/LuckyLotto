package com.example.luckylotto.ui.view.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.CustomBlue
import com.example.luckylotto.ui.theme.CustomRed
import com.example.luckylotto.ui.view.components.CircularCountDownTimer
import com.example.luckylotto.ui.view.components.PoolCardId
import com.example.luckylotto.ui.view.components.TicketsBought
import com.example.luckylotto.ui.viewmodel.MainViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PurchaseDialog(mainViewModel: MainViewModel, pool: Pool, onDismissRequest: (Boolean) -> Unit, updatePool: (String) -> Unit, createNewTicket: () -> Unit, sharePool: () -> Unit) {
    var enoughCoins by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Card(
            modifier = Modifier.fillMaxWidth().height(400.dp).padding(0.dp, 10.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = pool.poolImage,
                    contentDescription = "Image from URL",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PoolIdRow(pool) { onDismissRequest(it) }
                    PoolMaxPrizeRow(pool)
                    PoolTicketBoughtRow(pool)
                    Spacer(modifier = Modifier.height(10.dp))
                    PoolUpdateAndShareButtonsRow(
                        pool = pool,
                        updatePool = { updatePool(it) },
                        sharePool = { sharePool() }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    PoolPurchaseButtonRow(
                        mainViewModel,
                        enoughCoins = { enoughCoins = it },
                        createNewTicket = { createNewTicket() }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    PoolTimerRow(pool)
                }
                // Custom Toast
                if(!enoughCoins) {
                    LaunchedEffect(1) {
                        delay(2000L)
                        enoughCoins = !enoughCoins
                    }
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(20.dp, 5.dp)
                            .background(color = CustomRed, shape = ShapeDefaults.Small)
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = " " + "you need 3 coins!" + " ", color = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
fun PoolTimerRow(pool: Pool) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )  {
        Icon(imageVector = ImageVector.vectorResource(R.drawable.timer), contentDescription = "Timer", tint = Color.Black)
        CircularCountDownTimer(pool.startTime, pool.closeTime,isVisible = { })
    }
}

@Composable
fun PoolPurchaseButtonRow(mainViewModel: MainViewModel, enoughCoins: (Boolean) -> Unit, createNewTicket: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )  {
        Button(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = {
                if(mainViewModel.checkEnoughCoins()) {
                    mainViewModel.decrementingThreeCoins()
                    createNewTicket()
                } else {
                    enoughCoins(false)
                }
            },
            shape = ShapeDefaults.Small,
            colors = ButtonColors(CustomBlue, CustomBlue, CustomBlue, CustomBlue)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Purchase Ticket",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    modifier =  Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.coin),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Coin image"
                )
            }
        }
    }
}

@Composable
fun PoolUpdateAndShareButtonsRow(pool: Pool, updatePool: (String) -> Unit, sharePool: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    )  {
        IconButton(
            onClick = { updatePool(pool.poolId) },
            modifier = Modifier.size(60.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = CustomBlue)
        ) {
            Icon(modifier = Modifier.size(35.dp), imageVector = ImageVector.vectorResource(
                R.drawable.synchronize), contentDescription = "Synchronize", tint = Color.White)
        }
        IconButton(
            onClick = { sharePool() },
            modifier = Modifier.size(60.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = CustomBlue)
        ) {
            Icon(modifier = Modifier.size(35.dp), imageVector = ImageVector.vectorResource(
                R.drawable.share), contentDescription = "Share", tint = Color.White)
        }
    }
}

@Composable
fun PoolTicketBoughtRow(pool: Pool) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )  {
        Icon(imageVector = ImageVector.vectorResource(R.drawable.group), contentDescription = "Number of players", tint = Color.Black)
        TicketsBought(pool.ticketsBought.toString(),pool.maxTickets.toString())
    }
}

@Composable
fun PoolMaxPrizeRow(pool: Pool) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = ImageVector.vectorResource(R.drawable.paid), contentDescription = "Max prize", tint = Color.Black)
        Spacer(modifier = Modifier.width(5.dp))
        Box {
            Text(
                modifier = Modifier.background(Color.White, shape = RoundedCornerShape(5.dp)),
                text = "${pool.maxPrize}€",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
    }
}

@Composable

private fun PoolIdRow(pool: Pool, onDismissRequest: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(0.9f)) {
            PoolCardId(poolId = pool.poolId)
        }
        Box(modifier = Modifier.weight(0.1f)) {
            IconButton(
                onClick = { onDismissRequest(false) },
                modifier = Modifier.size(30.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = CustomRed, contentColor = Color.Red)
            ) {
                Icon(modifier = Modifier.size(20.dp), imageVector = ImageVector.vectorResource(
                    R.drawable.close), contentDescription = "Close", tint = Color.White)
            }
        }
    }
}