package com.example.luckylotto.ui.view.profile

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.luckylotto.data.core.admob.Admob
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.model.Wallet
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.CustomBlue
import com.example.luckylotto.ui.theme.CustomDarkBlue
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfileCard(mainViewModel: MainViewModel, modifier: Modifier) {
    val localContext = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    Surface(modifier = modifier.shadow(elevation = 20.dp, spotColor = Color.Black, ambientColor = Color.Black, shape = RoundedCornerShape(15.dp))) {
        Box(modifier = modifier.background(AppGreen).fillMaxWidth().padding(10.dp)) {
            Column(modifier = modifier.fillMaxWidth()) {
                Column(modifier = modifier.fillMaxWidth().height(100.dp)) {
                    Row(
                        modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(
                            modifier = modifier.height(100.dp).width(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CoinCounter(mainViewModel.wallet)
                        }
                        Box(
                            modifier = modifier.height(120.dp).width(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            UserLogo()
                        }
                        Box(
                            modifier = modifier.height(100.dp).width(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LogoutButton { FirebaseAuthentication.instance.signOutFirebaseAuthentication(mainViewModel, AppNavigation.instance.appNavigation()[0]) }
                        }
                    }
                }
                Spacer(modifier = modifier.height(10.dp))
                Column(modifier = modifier.fillMaxWidth().height(50.dp).padding(15.dp, 0.dp)) {
                    PurchaseCoins(
                        isLoading,
                        loading = { isLoading = it },
                        onClick = {
                            Admob.instance.loadRewardedInterstitialAd(localContext) {
                                mainViewModel.incrementCoin()
                                isLoading = false
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CoinCounter(coins: StateFlow<Wallet?>) {
    val amount = coins.collectAsState().value?.coins
    Row {
        Box(contentAlignment = Alignment.Center) {
            ImageIcon(50, R.drawable.coin,"Coin counter")
        }
        Spacer(modifier = Modifier.width(5.dp))
        Box(
            modifier = Modifier.align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = amount.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}

@Composable
private fun ImageIcon(size: Int,painter: Int,contentDescription: String) {
    Image(
        modifier =  Modifier.size(size.dp),
        painter = painterResource(id = painter),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription
    )
}

@Composable
private fun UserLogo() {
    AsyncImage(
        model = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.photoUrl.toString(),
        modifier = Modifier.size(100.dp).clip(CircleShape),
        contentDescription = "User image profile"
    )
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    IconButton(
        modifier =  Modifier.size(85.dp),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier.size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageIcon(50,R.drawable.logout,"Logout")
        }
    }
}

@Composable
private fun PurchaseCoins(isLoading: Boolean, loading: (Boolean) -> Unit, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        onClick = {
            onClick()
            loading(true)
        },
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, color = CustomDarkBlue),
        color = CustomBlue
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                ImageIcon(50,R.drawable.ads,"Ads")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Purchase coins",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            if(isLoading) {
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp), strokeWidth = 3.dp, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun MailCard(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.size(85.dp),
        onClick = { onClick() }
    ) {
        MessageCounter()
    }
}

@Composable
private fun MessageCounter() {
    Box(
        modifier = Modifier.size(70.dp),
        contentAlignment = Alignment.Center
    ){
        ImageIcon(70,R.drawable.mail,"Message counter")
        Box(modifier = Modifier.offset(x = 20.dp, y = (-10).dp).clip(CircleShape).background(Color.Red)) {
            Text(
                modifier = Modifier.size(30.dp).align(Alignment.Center).padding(0.dp, 3.dp),
                textAlign = TextAlign.Center,
                text = "+9",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun WonCounter() {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ImageIcon(50,R.drawable.win,"Coin counter")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
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