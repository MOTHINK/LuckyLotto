package com.example.luckylotto.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.ui.viewmodel.MainViewModel

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
    val itemList = List(imageList.size) { it }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                // Use the 'items' function to create a LazyColumn from a list
                items(itemList) { item ->
                    PoolCard(poolId = item, imageList)
                }
            }
        }
    }
}


@Composable
fun PoolCard(poolId: Int,imageListItem: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(0.dp, 5.dp)
    ) {
        // You can put content inside the card here
        Box {
            // Add the image inside the card
            AsyncImage(
                model = imageListItem[poolId],  // URL of the image
                contentDescription = "Image from URL",
                modifier = Modifier
                    .fillMaxWidth()   // Makes the image fill the width of the card
                    .height(200.dp),
                contentScale = ContentScale.Crop  // Scale the image to fill the container
            )
            Box {
                Column {
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
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                    // Max prize
                    Spacer(modifier = Modifier.height(100.dp))
                    //
                    Text(
                        text = "0/1000000",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                }
            }
        }
    }
}