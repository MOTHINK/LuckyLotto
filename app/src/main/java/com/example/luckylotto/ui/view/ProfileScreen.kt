package com.example.luckylotto.ui.view

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
            Column(modifier = modifier.height(300.dp)) {
                Column {
                    Row(
                        modifier = modifier
                            .background(Color.Black)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = modifier
                                .background(Color.Red)
                                .height(200.dp)
                                .width(100.dp)
                        )
                        Box(
                            modifier = modifier
                                .background(Color.Blue)
                                .height(200.dp)
                                .width(100.dp)
                        )
                        Box(
                            modifier = modifier
                                .background(Color.Yellow)
                                .height(200.dp)
                                .width(100.dp)
                        )
                    }
                }
            }
        }
    }
}

/*

Column {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    AsyncImage(
                        model = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.photoUrl.toString(),
                        modifier = modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .align(Alignment.Center),
                        contentDescription = "User image profile"
                    )
                }
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Text(
                        modifier = modifier.align(Alignment.Center),
                        text = "Welcome: " + FirebaseAuthentication.instance.getFirebaseCurrentUser()?.displayName.toString(),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }
                Box(
                    modifier = modifier
                        .background(Color.Blue)
                        .fillMaxWidth()
                        .height(130.dp),
                )
            }

 */