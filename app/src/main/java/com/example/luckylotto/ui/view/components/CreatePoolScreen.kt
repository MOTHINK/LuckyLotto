package com.example.luckylotto.ui.view.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.CustomBlue
import com.example.luckylotto.ui.theme.CustomDarkBlue
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlin.math.round

@Composable
fun CreatePoolScreen(mainViewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val maxTicketValues = listOf(50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000)
    val maxTimeValues = listOf(1,6,12,24,48,72,168,336,672)

    var maxTickets by remember { mutableIntStateOf(maxTicketValues[0]) }
    var closeTime by remember { mutableLongStateOf(3600000L*maxTimeValues[0]) }
    var poolImage by remember { mutableStateOf(mainViewModel.imageList[0]) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(10.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                color = AppGreen,
                shape = RoundedCornerShape(10.dp)
            )
        ) {
            Column {
                // Title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
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
                Spacer(modifier = Modifier.height(10.dp))
                // Pool ID
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp)
                                .background(
                                    CustomBlue,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            text = " "+"Maximum Tickets"+" ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomSlider(maxTicketValues,"Tickets") { maxTickets = it }
                    }
                }
                // Max Tickets
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp)
                                .background(
                                    CustomBlue,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            text = " "+"Maximum Time"+" ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomSlider(maxTimeValues,"Hours") { closeTime = 3600000L*it }
                    }
                }
                // Image Picker
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp)
                                .background(
                                    CustomBlue,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            text = " "+"Background"+" ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        var selectedImage by remember { mutableStateOf("") }
                        var selectedImagePosition by remember { mutableIntStateOf(-1) }

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(20.dp, 0.dp)
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(5.dp)
                                )
                        ) {
                            items(mainViewModel.imageList.size) {
                                SelectedImage(
                                    mainViewModel.imageList[it],
                                    it,
                                    selectedImagePosition,
                                    poolImageValue = { poolImage ->
                                        selectedImage = poolImage
                                    },
                                    poolImagePosition = { position ->
                                        selectedImagePosition = position
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectedImage(poolImage: String, position: Int, selectedPosition: Int, poolImageValue: (String) -> Unit, poolImagePosition: (Int) -> Unit) {

    var poolImageBorderColor by remember { mutableStateOf(Color.Transparent) }

    if(selectedPosition == position) {
        poolImageBorderColor = CustomDarkBlue
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(5.dp)
            .border(
                width = 4.dp,
                color = poolImageBorderColor,
                shape = RoundedCornerShape(5.dp)
            )
            .background(
                Color.Gray,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                poolImageValue(poolImage)
                poolImageBorderColor = if(selectedPosition != position) {
                    poolImagePosition(position)
                    CustomDarkBlue
                } else {
                    poolImagePosition(-1)
                    Color.White
                }
                Log.d("SelectedImage", "the selected image is: $poolImage + and the position is: $position")
            }
    ) {
        AsyncImage(
            model = poolImage,
            contentDescription = "Image from URL",
            modifier = Modifier
                .size(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun CustomSlider(sliderValues: List<Int>, value: String, result: (Int) -> Unit) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
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
                .padding(20.dp, 0.dp)
                .background(
                    CustomBlue,
                    shape = RoundedCornerShape(20.dp)
                )
                .align(Alignment.End),
            text = " "+sliderValues[round(sliderPosition).toInt()].toString() + " " + value+" ",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}