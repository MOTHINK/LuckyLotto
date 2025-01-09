package com.example.luckylotto.ui.view.components

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.luckylotto.R
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.AppGreen
import com.example.luckylotto.ui.viewmodel.MainViewModel
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.ShapeCornerRadius

@Composable
fun NewCustomBottomBar(mainViewModel: MainViewModel, modifier: Modifier) {
    val appMainColor = AppGreen
    val selectedIndex by mainViewModel.navBarIndex.collectAsState()
    Log.d("checking12345","User is registred")
    AnimatedNavigationBar(
        modifier = modifier.padding(10.dp, 0.dp, 10.dp, 10.dp).fillMaxWidth(),
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
            BottomNavbarButton(
                {
                    AppNavigation.instance.appNavigation()[2]()
                },
                R.drawable.ticket, blackColor, playDescription
            )
        }
        if(selectedIndex == 1) {
            BottomNavbarButton({},R.drawable.add_box_filled, blackColor, playDescription)
        } else {
            BottomNavbarButton(
                {
                    AppNavigation.instance.appNavigation()[3]()
                },
                R.drawable.add_box, blackColor, playDescription
            )
        }
        if(selectedIndex == 2) {
            BottomNavbarButton({},R.drawable.profile_filled, blackColor, profileDescription)
        } else {
            BottomNavbarButton(
                {
                    AppNavigation.instance.appNavigation()[1]()
                },
                R.drawable.profile, blackColor, profileDescription
            )
        }
    }
}

@Composable
private fun BottomNavbarButton(onClick: () -> Unit, iconResource: Int, color: Color, contentDescription: String) {
    IconButton(
        modifier = Modifier.height(60.dp),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier.size(80.dp)
        )
    }
}