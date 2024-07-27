package com.example.luckylotto.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.luckylotto.R
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.ui.theme.AppGreen

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        ProfileCard(Modifier)
    }
}

@Composable
private fun ProfileCard(modifier: Modifier) {
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
                .height(300.dp)
                .padding(10.dp)
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
                        MailCard()
                    }
                    Box(
                        modifier = modifier
                            .height(120.dp)
                            .width(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        UserLogo(modifier)
                    }
                    Box(modifier = modifier
                        .height(100.dp)
                        .width(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LogoutButton()
                    }
                }
            }
        }
    }
}

@Composable
private fun UserLogo(modifier: Modifier) {
    AsyncImage(
        model = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.photoUrl.toString(),
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape),
        contentDescription = "User image profile"
    )
}


@Composable
private fun MailCard() {
    IconButton(
        modifier =  Modifier
            .size(85.dp),
        onClick = { /* Handle button click */ }
    ) {
        MessageCounter()
    }
}

@Composable
private fun PurchaseCoins() {
    IconButton(
        modifier =  Modifier
            .size(85.dp)
            .background(Color.Black),
        onClick = { /* Handle button click */ }
    ) {
        // Code here next...
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
        Image(
            modifier =  Modifier
                .size(size.dp),
            painter = painterResource(id = R.drawable.mail),
            contentScale = ContentScale.Crop,
            contentDescription = "Mail"
        )
        Box(
            modifier = Modifier
                .offset(x = 20.dp, y = (-10).dp)
                .clip(CircleShape)
                .background(Color.Red)
        ) {
            Text(
                modifier = Modifier.size(30.dp)
                    .align(Alignment.Center)
                    .padding(0.dp,3.dp),
                textAlign = TextAlign.Center,
                text = "+9",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoutButton() {
    val size = 50
    IconButton(
        modifier =  Modifier
            .size(85.dp),
        onClick = { /* Handle button click */ }
    ) {
        Box(
            modifier = Modifier
                .size(size.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(size.dp),
                painter = painterResource(id = R.drawable.logout),
                contentScale = ContentScale.Crop,
                contentDescription = "Mail"
            )
        }
    }
}