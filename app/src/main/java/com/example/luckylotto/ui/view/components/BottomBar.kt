package com.example.luckylotto.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.luckylotto.R
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.theme.MilkyYellow

val CardColor = MilkyYellow
val SecondaryColor = AppGreen
val blackColor = Color.Black
val whiteColor = Color.White

const val profileDescription = "Profile"
const val playDescription = "Play"

@Composable
private fun BottomBar(modifier: Modifier = Modifier) {

    var navNum by remember { mutableIntStateOf(1) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(SecondaryColor)
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            BottomNavbarButton({ AppNavigation.instance.appNavigation()[2]() },R.drawable.ticket, blackColor, playDescription)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            BottomNavbarButton({ AppNavigation.instance.appNavigation()[1]() },R.drawable.mail, blackColor, profileDescription)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            BottomNavbarButton({ AppNavigation.instance.appNavigation()[1]() },R.drawable.account_circle, blackColor, profileDescription)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBottomBar() {
    Box {
        BottomBar(modifier=Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
private fun BoxScope.FloatingButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(bottom = 35.dp)
            .clip(CircleShape)
            .background(whiteColor)
            .align(Alignment.BottomCenter)
            .padding(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add_box_filled),
            contentDescription = "add",
            tint = Color.Black,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
private fun BottomNavbarButton(onClick: () -> Unit, iconResource: Int, color: Color, contentDescription: String) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier
                .size(50.dp)
        )
    }
}