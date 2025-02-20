package com.example.luckylotto.ui.view.play

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.view.components.CircularCountDownTimer
import com.example.luckylotto.ui.view.components.PoolCardId
import com.example.luckylotto.ui.view.components.PoolMaxPrize
import com.example.luckylotto.ui.view.components.TicketsBought
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun PoolCard(pool: Pool, mainViewModel: MainViewModel, rememberCoroutineScope: CoroutineScope) {
    val context = LocalContext.current
    var showUp by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }

    if(showUp) {
        PurchaseDialog(
            mainViewModel,
            pool,
            onDismissRequest = { showUp = it },
            updatePool = { rememberCoroutineScope.launch { mainViewModel.updateTicketAndPoolByPoolId(it) } },
            createNewTicket = {
                rememberCoroutineScope.launch {
                    if(this.async { mainViewModel.createNewTicket(mainViewModel.firebaseDB,pool) }.await()) {
                        showUp = false
                        AppNavigation.instance.appNavigation()[1]()
                        mainViewModel.setSnackBarMessage(2)
                    }
                }
            },
            sharePool = { mainViewModel.sharePool(context,pool.poolId) }
        )
    }

    if(isVisible) {
        Card(modifier = Modifier.fillMaxSize().padding(0.dp, 5.dp).clickable { showUp = true }) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = pool.poolImage,
                    contentDescription = "Image from URL",
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    contentScale = ContentScale.Crop
                )
                Box {
                    Column {
                        PoolCardId(poolId = pool.poolId)
                        PoolMaxPrize(pool.maxPrize.toString())
                        Row(
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TicketsBought(pool.ticketsBought.toString(),pool.maxTickets.toString())
                            CircularCountDownTimer(pool.startTime, pool.closeTime,isVisible = { isVisible = it })
                        }
                    }
                }
            }
        }
    }
}