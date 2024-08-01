package com.example.luckylotto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.LuckyLottoTheme
import com.example.luckylotto.ui.view.components.NewCustomBottomBar
import androidx.compose.runtime.Composable as Composable1

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAuthentication.instance.initializeFirebaseAuth()

        installSplashScreen()

        setContent {
            LuckyLottoTheme {
                Scaffold(
                    bottomBar = {
                        if(FirebaseAuthentication.instance.getFirebaseCurrentUser() != null) NewCustomBottomBar(Modifier)
                    }
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        navController = rememberNavController()
                        AppNavigation.instance.InitializeNavigation(navController)
                        if(FirebaseAuthentication.instance.getFirebaseCurrentUser() != null) AppNavigation.instance.appNavigation()[1]()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}