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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.LotteryCardCountDownTimer
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

@Composable
fun PlayScreen(mainViewModel: MainViewModel) {
    val imageList: List<String> = listOf(
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp"
    )

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
                TopNavBarSearchPoolCard()
                Spacer(modifier = Modifier.height(5.dp))
                PoolCardList(imageList,mainViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNavBarSearchPoolCard() {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                AppGreen,
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
                    onValueChange = { text = it },
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
fun PoolCardList(imageList: List<String>, mainViewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val pools by mainViewModel.pools.collectAsState()

    LaunchedEffect(pools) {
        coroutineScope.launch {
            mainViewModel.getAllPoolsFromDatabase()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(mainViewModel.pools.value.size) {
            PoolCard(pool = pools[it], imageListItem = imageList[it])
        }
    }
}

@Composable
fun PoolCard(pool: Pool, imageListItem: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(0.dp, 5.dp)
    ) {
        Box {
            AsyncImage(
                model = imageListItem,
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
                        CircularCountDownTimer(System.currentTimeMillis()+3600000)
                    }
                }
            }
        }
    }
}
@Composable
fun CircularCountDownTimer(time:Long) {
    val progressTime = 1.0f
    Row {
        Text(
            text = "00:00",
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
            progress = { progressTime },
            modifier = Modifier.size(40.dp),
            color = LotteryCardCountDownTimer,
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
        text = "000000$poolId",
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
                    text = maxPrize+"€",
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