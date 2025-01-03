package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.CustomBlue
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun CreateNewPoolButton(mainViewModel: MainViewModel, maxTickets: Int, closeTime: Long, poolImage: String, isPrivate: Boolean) {
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    Button(
        modifier = Modifier.padding(20.dp, 0.dp).fillMaxWidth().height(50.dp),
        onClick = {
            coroutineScope.launch {
                // We also need to check if we have enough coins to purchase a ticket.
                isLoading = true

                if(this.async { mainViewModel.createPoolAndGetTicket(mainViewModel.firebaseDB,maxTickets,closeTime,poolImage,isPrivate) }.await()) {
                    mainViewModel.setSnackBarMessage(1)
                    AppNavigation.instance.appNavigation()[1]()
                    isLoading = false
                }

            }

        },
        shape = ShapeDefaults.Small,
        colors = ButtonColors(CustomBlue, CustomBlue, CustomBlue, CustomBlue)
    ) {
        Row {
            Text(
                text = "Create new pool",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        if(isLoading) {
            Spacer(modifier = Modifier.width(10.dp))
            CircularProgressIndicator(modifier = Modifier.size(30.dp), strokeWidth = 3.dp, color = Color.White)
        }
    }
}