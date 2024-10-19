package com.example.luckylotto.ui.view

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.core.firebase.GoogleAuthenticationCredentialManager
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.Purple40
import com.example.luckylotto.ui.theme.PurpleGrey40
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun ProfileScreen(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        ProfileCard(mainViewModel,Modifier)
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
        modifier = Modifier.fillMaxWidth().height(50.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, color = PurpleGrey40),
        color = Purple40
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