package com.example.luckylotto

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luckylotto.data.core.admob.Admob
import com.example.luckylotto.data.database.AppContainer
import com.example.luckylotto.data.database.AppDataContainer
import com.example.luckylotto.data.repository.pool_repository.PoolRepository
import com.example.luckylotto.data.repository.ticket_repository.TicketRepository
import com.example.luckylotto.data.repository.wallet_repository.WalletRepository
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.LuckyLottoTheme
import com.example.luckylotto.ui.view.components.NewCustomBottomBar
import com.example.luckylotto.ui.view.create.SnackBarMessage
import com.example.luckylotto.ui.viewmodel.MainViewModel
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity(), OnUserEarnedRewardListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var container: AppContainer
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Mobile Ads
        CoroutineScope(Dispatchers.IO).launch {
            Admob.instance.initializeMobileAds(this@MainActivity)
        }
        // Initialize database container
        container = AppDataContainer(this)
        // Setting Splash Screen
        installSplashScreen()

        setContent {
            LuckyLottoTheme {
                // Initialize ViewModel
                mainViewModel = InitializeMainViewModels(container.poolRepository,container.ticketRepository,container.walletRepository)
                // App Structure
                Scaffold(
                    bottomBar = { if(mainViewModel.user.collectAsState().value != null) NewCustomBottomBar(mainViewModel,Modifier) },
                    snackbarHost = { SnackBarMessage(mainViewModel) }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation.instance.InitializeNavigation(mainViewModel)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    @SuppressLint("ComposableNaming")
    @Composable
    private fun InitializeMainViewModels(poolRepository: PoolRepository, ticketRepository: TicketRepository, walletRepository: WalletRepository): MainViewModel {
        return viewModel<MainViewModel>(
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(poolRepository,ticketRepository,walletRepository) as T
                }
            }
        )
    }

    override fun onUserEarnedReward(rewardItem: RewardItem) {
        Log.d("USER_EARNED_REWARD", "User earned reward. ${rewardItem.toString()}")
    }

}