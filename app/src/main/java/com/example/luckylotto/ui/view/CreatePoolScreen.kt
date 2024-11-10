package com.example.luckylotto.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.CustomBlue
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.round

@Composable
fun CreatePoolScreen(mainViewModel: MainViewModel) {
    var maxTickets by remember { mutableIntStateOf(mainViewModel.maxTicketValues[0]) }
    var closeTime by remember { mutableLongStateOf(3600000L*mainViewModel.maxTimeValues[0]) }
    var poolImage by remember { mutableStateOf(mainViewModel.imageList[0]) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White).padding(10.dp)) {
        Box(modifier = Modifier.fillMaxSize().background(color = AppGreen, shape = RoundedCornerShape(10.dp))) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    TitleRow()
                    Spacer(modifier = Modifier.height(10.dp))
                    MaximumTicketsRow(mainViewModel) { value -> maxTickets = value }
                    Spacer(modifier = Modifier.height(10.dp))
                    MaximumTimeRow(mainViewModel) { value -> closeTime = value }
                    Spacer(modifier = Modifier.height(10.dp))
                    ImagePickerRow(mainViewModel) { value -> poolImage = value }
                    Spacer(modifier = Modifier.height(10.dp))
                    CreatePoolButtonRow(mainViewModel,maxTickets,closeTime,poolImage)
                }
            }
        }
    }
}

@Composable
fun TitleRow() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .background(
                        CustomBlue,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .align(Alignment.CenterHorizontally),
                text = " "+"Create your own pool"+" ",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MaximumTicketsRow(mainViewModel: MainViewModel, maxTicketsValue: (Int) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                LabelText("Maximum Tickets")
            }
            CustomSlider(mainViewModel.maxTicketValues,"Tickets") { maxTicketsValue(it) }
        }
    }
}
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
@Composable
fun ImagePickerRow(mainViewModel: MainViewModel, poolImageValue: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                LabelText("Background image")
            }

            var selectedImageIndex by remember { mutableIntStateOf(0) }

            HorizontalImagePicker(
                mainViewModel,
                selectedImageIndex,
                selectedImageIndexValue = { value ->
                    selectedImageIndex = value
                },
                poolImageValue = { value ->
                    poolImageValue(value)

                }
            )
        }
    }
}

@Composable
fun CreatePoolButtonRow(mainViewModel: MainViewModel,maxTickets: Int,closeTime: Long,poolImage: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CreateNewPoolButton(mainViewModel,maxTickets,closeTime,poolImage)
        }
    }
}



@Composable
fun CreateNewPoolButton(mainViewModel: MainViewModel, maxTickets: Int, closeTime: Long, poolImage: String) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        modifier = Modifier
            .padding(20.dp, 0.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = {
            Log.d("CreateNewPoolButtonTest", "$maxTickets - $closeTime - $poolImage")
            coroutineScope.launch {
                mainViewModel.createNewPoolTest(maxTickets,closeTime,poolImage)
            }
        },
        shape = ShapeDefaults.Small,
        colors = ButtonColors(CustomBlue,CustomBlue,CustomBlue,CustomBlue)
    ) {
        Row {
            Text(
                text = "Create new pool",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HorizontalImagePicker(mainViewModel: MainViewModel, selectedImageIndex: Int,selectedImageIndexValue: (Int) -> Unit, poolImageValue: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(20.dp, 0.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        items(mainViewModel.imageList.size) {
            SelectedImage(
                mainViewModel.imageList[it],
                it,
                selectedImageIndex,
                indexChange = { value ->
                    selectedImageIndexValue(value)
                },
                selectedPoolImage = { value ->
                    poolImageValue(mainViewModel.imageList[value])
                }
            )
        }
    }
}

@Composable
fun SelectedImage(poolImage: String, position: Int, index: Int, indexChange: (Int) -> Unit, selectedPoolImage: (Int) -> Unit) {
    var poolImageBorderColor by remember { mutableStateOf(Color.Transparent) }

    LaunchedEffect(key1 = index) {
        poolImageBorderColor = if (position == index) {
            CustomBlue
        } else {
            Color.Transparent
        }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(150.dp)
            .clip(CircleShape)
            .padding(5.dp)
            .border(
                width = 5.dp,
                color = poolImageBorderColor,
                shape = CircleShape
            )
            .clickable {
                indexChange(position)
                selectedPoolImage(position)
            }
    ) {
        AsyncImage(
            model = poolImage,
            contentDescription = "Image from URL",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun CustomSlider(sliderValues: List<Int>, value: String, result: (Int) -> Unit) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            modifier = Modifier.padding(20.dp,0.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = CustomBlue,
                activeTrackColor = CustomBlue,
                inactiveTrackColor = Color.White,
            ),
            steps = sliderValues.size - 2,
            valueRange = 0f..(sliderValues.size-1).toFloat()
        )
        result(sliderValues[round(sliderPosition).toInt()])
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(5.dp)
                ),
            text = " " + sliderValues[round(sliderPosition).toInt()].toString() + " " + value +" ",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LabelText(text: String) {
    Text(
        modifier = Modifier
            .padding(20.dp)
            .background(
                CustomBlue,
                shape = RoundedCornerShape(5.dp)
            ),
        text = " $text ",
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}