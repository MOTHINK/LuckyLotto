package com.example.luckylotto.ui.view.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.luckylotto.R
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.AppGreen
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.ShapeCornerRadius


@SuppressLint("Range")
@Composable
fun NewCustomBottomBar(modifier: Modifier) {

    val appMainColor = AppGreen
    var selectedIndex by remember { mutableIntStateOf(2) }

    AnimatedNavigationBar(
        modifier = modifier
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
            .fillMaxWidth()
        ,
        selectedIndex = selectedIndex,
        barColor = appMainColor,
        ballColor = appMainColor,
        cornerRadius = ShapeCornerRadius(50F,50F,50F,50F),
        ballAnimation = Straight(tween(300)),
        indentAnimation = Height(tween(300))
    ) {

        if(selectedIndex == 0) {
            BottomNavbarButton({},R.drawable.ticket_filled, blackColor, playDescription)
        } else {
            BottomNavbarButton({
                AppNavigation.instance.appNavigation()[2]()
                selectedIndex = 0
            },R.drawable.ticket, blackColor, playDescription)
        }

        if(selectedIndex == 1) {
            BottomNavbarButton({},R.drawable.add_box_filled, blackColor, playDescription)
        } else {
            BottomNavbarButton({
                AppNavigation.instance.appNavigation()[2]()
                selectedIndex = 1
            },R.drawable.add_box, blackColor, playDescription)
        }

        if(selectedIndex == 2) {
            BottomNavbarButton({},R.drawable.profile_filled, blackColor, profileDescription)
        } else {
            BottomNavbarButton({
                AppNavigation.instance.appNavigation()[1]()
                selectedIndex = 2
            },R.drawable.profile, blackColor, profileDescription)
        }

    }

}

@Composable
private fun BottomNavbarButton(onClick: () -> Unit, iconResource: Int, color: Color, contentDescription: String) {
    IconButton(
        modifier = Modifier.height(60.dp),
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier.size(80.dp)
        )
    }
}