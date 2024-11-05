package com.example.luckylotto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.database.AppContainer
import com.example.luckylotto.data.database.AppDataContainer
import com.example.luckylotto.data.database.LuckyLottoDatabase
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.LuckyLottoTheme
import com.example.luckylotto.ui.view.components.NewCustomBottomBar
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val imageList: List<String> = listOf(
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp"
    )

    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel
    private lateinit var container: AppContainer
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container = AppDataContainer(this)
        mainViewModel = MainViewModel(container.poolRepository)
        FirebaseAuthentication.instance.initializeFirebaseAuth()

        lifecycleScope.launch {
            for (i in 11..20) {
                container.poolRepository.insertPool(Pool("id$i",10.0*i,1000*i,50*i,System.currentTimeMillis(),System.currentTimeMillis()+(60000L*60),imageList[i-10]))
            }
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            container.poolRepository.deletePoolById("id5")
//        }

        if(mainViewModel.user.value == null) {
            mainViewModel.setFirebaseUser(FirebaseAuthentication.instance.getFirebaseCurrentUser())
        }

        installSplashScreen()

        setContent {
            LuckyLottoTheme {
                Scaffold(
                    bottomBar = { if(mainViewModel.user.collectAsState().value != null) NewCustomBottomBar(Modifier) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        navController = rememberNavController()
                        AppNavigation.instance.InitializeNavigation(navController,mainViewModel)
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