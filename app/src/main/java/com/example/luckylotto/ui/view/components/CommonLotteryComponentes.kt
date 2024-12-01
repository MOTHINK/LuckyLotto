package com.example.luckylotto.ui.view.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckylotto.R
import com.example.luckylotto.ui.theme.CustomLightBlue
import com.example.luckylotto.ui.theme.CustomRed
import com.example.luckylotto.utils.CustomTimeFormatter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlin.math.max

@Composable
fun CircularCountDownTimer(startTime: Long, endTime: Long, isVisible: (Boolean) -> Unit) {
    var circularProgressIndicatorColor by remember { mutableStateOf(CustomLightBlue) }
    var timeLeftMillis by remember { mutableLongStateOf(endTime-System.currentTimeMillis()) }
    val percentage = ((timeLeftMillis.toFloat() / (endTime-startTime)) * 100)/100

    LaunchedEffect(percentage) {
        timeLeftMillis = endTime-System.currentTimeMillis()
        while (timeLeftMillis > 0) {
            delay(1000L)
            timeLeftMillis = max(timeLeftMillis-1000L,0)
            Log.d("progressCircularCountDown", "> $percentage% - TimeLeft: $timeLeftMillis - StartTime: $startTime")
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
            .height(45.dp)
            .padding(10.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(5.dp)
            ),
        maxLines = 1
    )
}

@Composable
fun PoolMaxPrize(maxPrize: String = "0000000") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
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


@Composable
fun CountDownDateTime(millis: Long) {
    val dateFormat = SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault())
    val date = Date(millis)

    Row {
        Text(
            text = dateFormat.format(date),
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
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun TicketNumbers(num: String, modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for(i in 0..5) {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${num[i]}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(3.dp))
            }
        }
    }
}