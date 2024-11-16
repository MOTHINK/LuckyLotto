package com.example.luckylotto.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.CustomBlue
import com.example.luckylotto.ui.theme.CustomDarkBlue
import com.example.luckylotto.ui.view.components.CountDownDateTime
import com.example.luckylotto.ui.view.components.PoolCardId
import com.example.luckylotto.ui.view.components.TicketsBought
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun ProfileScreen(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()) {
            ProfileCard(mainViewModel,Modifier)
            Spacer(modifier = Modifier.height(10.dp))
            TicketCardList(mainViewModel)
        }
    }
}

@Composable
fun TicketCardList(mainViewModel: MainViewModel) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()) {
        items(20) {
            TicketCard(mainViewModel,it)
            if(it < 19) Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun TicketCard(mainViewModel: MainViewModel, i: Int) {
    var showUp by remember { mutableStateOf(false) }
    if(showUp) {
        DialogWithImage({}, {}, painterResource(id = R.drawable.coin), "")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                showUp = !showUp
            }
    ) {
        AsyncImage(
            model = mainViewModel.imageList[i],
            contentDescription = "Image from URL",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    PoolCardId(poolId = "0")
                }
                TicketNumbers("123456")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TicketsBought("0","1000")
                    CountDownDateTime(1731893658265L) // close time
                }
            }
        }
    }
}

@Composable
fun TicketNumbers(num: String) {
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
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${num[i]}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(3.dp))
            }
        }
    }
}

@Composable
private fun ProfileCard(mainViewModel: MainViewModel, modifier: Modifier) {
    Surface(
        modifier = modifier.shadow(
            elevation = 20.dp,
            spotColor = Color.Black,
            ambientColor = Color.Black,
            shape = RoundedCornerShape(15.dp)
        )
    ) {
        Box(
            modifier = modifier
                .background(AppGreen)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(modifier = modifier
                            .height(100.dp)
                            .width(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CoinCounter()
                        }
                        Box(
                            modifier = modifier
                                .height(120.dp)
                                .width(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            UserLogo()
                        }
                        Box(modifier = modifier
                            .height(100.dp)
                            .width(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LogoutButton { FirebaseAuthentication.instance.signOutFirebaseAuthentication(mainViewModel,AppNavigation.instance.appNavigation()[0]) }
                        }
                    }
                }
                Spacer(modifier = modifier.height(10.dp))
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(15.dp, 0.dp)
                ) {
                    PurchaseCoins {}
                }
            }
        }
    }
}

@Composable
private fun UserLogo() {
    AsyncImage(
        model = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.photoUrl.toString(),
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape),
        contentDescription = "User image profile"
    )
}


@Composable
private fun MailCard(onClick: () -> Unit) {
    IconButton(
        modifier =  Modifier
            .size(85.dp),
        onClick = { onClick() }
    ) {
        MessageCounter()
    }
}

@Composable
private fun PurchaseCoins(onClick: () -> Unit) {
    val size = 50
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, color = CustomDarkBlue),
        color = CustomBlue
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                ImageIcon(size,R.drawable.ads,"Ads")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Purchase coins",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun MessageCounter() {
    val size = 70
    Box(
        modifier = Modifier
            .size(size.dp),
        contentAlignment = Alignment.Center
    ){
        ImageIcon(size,R.drawable.mail,"Message counter")
        Box(
            modifier = Modifier
                .offset(x = 20.dp, y = (-10).dp)
                .clip(CircleShape)
                .background(Color.Red)
        ) {
            Text(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
                    .padding(0.dp, 3.dp),
                textAlign = TextAlign.Center,
                text = "+9",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    val size = 50
    IconButton(
        modifier =  Modifier
            .size(85.dp),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(size.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageIcon(size,R.drawable.logout,"Logout")
        }
    }
}

@Composable
private fun ImageIcon(size: Int,painter: Int,contentDescription: String) {
    Image(
        modifier =  Modifier
            .size(size.dp),
        painter = painterResource(id = painter),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription
    )
}

@Composable
private fun CoinCounter(numberOfCoins: String = "000") {
    val size = 50
    Row {
        Box(
            contentAlignment = Alignment.Center
        ) {
            ImageIcon(size,R.drawable.coin,"Coin counter")
        }
        Spacer(modifier = Modifier.width(5.dp))
        Box(
            modifier = Modifier.align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = numberOfCoins,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}

@Composable
private fun WonCounter() {
    val size = 50
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ImageIcon(size,R.drawable.win,"Coin counter")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "0",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    painter: Painter,
    imageDescription: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painter,
                    contentDescription = imageDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(160.dp)
                )
                Text(
                    text = "This is a dialog with buttons and an image.",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

