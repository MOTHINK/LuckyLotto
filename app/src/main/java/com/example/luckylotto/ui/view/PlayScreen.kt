package com.example.luckylotto.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.CustomLightBlue
import com.example.luckylotto.ui.theme.CustomRed
import com.example.luckylotto.ui.viewmodel.MainViewModel
import com.example.luckylotto.utils.CustomTimeFormatter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlin.math.max

@Composable
fun PlayScreen(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
        val filteredPools = pools.filter { it.poolId.startsWith(poolSearchText) && it.closeTime >= System.currentTimeMillis() }
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
                .fillMaxWidth()
                .height(200.dp)
                .padding(0.dp, 5.dp)
        ) {
            Box {
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
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TicketsBought(pool.ticketsBought.toString(),pool.maxTickets.toString())
                            CircularCountDownTimer(pool.startTime,pool.closeTime) { isVisible = it }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun CircularCountDownTimer(startTime: Long, endTime: Long, isVisible: (Boolean) -> Unit) {
    var circularProgressIndicatorColor by remember { mutableStateOf(CustomLightBlue) }
    var timeLeftMillis by remember { mutableLongStateOf(endTime-System.currentTimeMillis()) }
    val percentage = ((timeLeftMillis.toFloat() / (endTime-startTime)) * 100)/100

    LaunchedEffect(percentage) {
        while (timeLeftMillis > 0) {
            delay(1000L)
            timeLeftMillis = max(timeLeftMillis-1000L,0)
            Log.d("progressCircularCountDown","> $percentage beside that i want to know the value of $timeLeftMillis - and starting time is: $startTime")
            if(percentage <= 0.25f ) {
                circularProgressIndicatorColor = CustomRed
            }
        }
        isVisible(false)
        this.coroutineContext.cancel()
    }

    Row {
        Text(
            text = CustomTimeFormatter.formatMillisToTime(timeLeftMillis),
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(5.dp)
                )
        )
        CircularProgressIndicator(
            progress = { percentage.toFloat() },
            modifier = Modifier.size(40.dp),
            color = circularProgressIndicatorColor,
            trackColor = Color.LightGray,
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}


@Composable
fun TicketsBought(tickets: String = "0000000", maxTickets: String = "0000000") {
    Text(
        text = "$tickets/$maxTickets",
        color = Color.Black,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(5.dp)
            )
    )
}

@Composable
fun PoolCardId(poolId: String) {
    Text(
        text = poolId,
        color = Color.Black,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(5.dp)
            )
    )
}

@Composable
fun PoolMaxPrize(maxPrize: String = "0000000") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                modifier =  Modifier
                    .size(80.dp),
                painter = painterResource(id = R.drawable.average),
                contentScale = ContentScale.Crop,
                contentDescription = "Average image"
            )
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.background(
                        Color.White,
                        shape = RoundedCornerShape(5.dp)
                    ),
                    text = "$maxPrize€",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
            Image(
                modifier =  Modifier
                    .size(80.dp),
                painter = painterResource(id = R.drawable.dollar_coin),
                contentScale = ContentScale.Crop,
                contentDescription = "Dollar coin image"
            )
        }
    }
}