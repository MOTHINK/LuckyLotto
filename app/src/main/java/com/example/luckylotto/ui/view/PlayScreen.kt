package com.example.luckylotto.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.viewmodel.MainViewModel
import com.example.luckylotto.ui.view.components.*

@Composable
fun PlayScreen(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TopNavBarSearchPoolCard(mainViewModel)
                Spacer(modifier = Modifier.height(5.dp))
                PoolCardList(mainViewModel)
            }
        }
    }
}

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

@Composable
fun PoolCardList(mainViewModel: MainViewModel) {
    val pools by mainViewModel.pools.collectAsState()
    val poolSearchText by mainViewModel.poolSearchText.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        val filteredPools = pools.filter { it.poolId.startsWith(poolSearchText) && it.closeTime >= System.currentTimeMillis() && !it.isPrivate}.toList()
        items(filteredPools.size) {
            PoolCard(filteredPools[it])
        }
    }
}

@Composable
fun PoolCard(pool: Pool) {
    var isVisible by remember { mutableStateOf(true) }
    if(isVisible) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 5.dp)
                .clickable {
                    // Purchase Ticket here
                }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = pool.poolImage,
                    contentDescription = "Image from URL",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
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
