package com.example.luckylotto

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.database.AppContainer
import com.example.luckylotto.data.database.AppDataContainer
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.repository.pool_repository.PoolRepository
import com.example.luckylotto.data.repository.ticket_repository.TicketRepository
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.LuckyLottoTheme
import com.example.luckylotto.ui.view.components.NewCustomBottomBar
import com.example.luckylotto.ui.view.create.CreatingPoolSnackBarMessage
import com.example.luckylotto.ui.viewmodel.MainViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var container: AppContainer
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container = AppDataContainer(this)

        installSplashScreen()

        setContent {
            LuckyLottoTheme {
                mainViewModel = InitializeMainViewModels(container.poolRepository,container.ticketRepository)
                Scaffold(
                    bottomBar = { if(mainViewModel.user.collectAsState().value != null) NewCustomBottomBar(mainViewModel,Modifier) },
                    snackbarHost = { CreatingPoolSnackBarMessage(mainViewModel) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.primary
                    ) {
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
    private fun InitializeMainViewModels(poolRepository: PoolRepository, ticketRepository: TicketRepository): MainViewModel {
        return viewModel<MainViewModel>(
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(poolRepository,ticketRepository) as T
                }
            }
        )
    }

    private fun createPoolsTesting() {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 11..20) {
                container.poolRepository.insertPool(Pool("id$i",FirebaseAuthentication.instance.getFirebaseCurrentUser()?.uid.toString(),"",10.0*i,1000*i,50*i,System.currentTimeMillis(),System.currentTimeMillis()+(60000L*60),mainViewModel.imageList[i-10]))
            }
        }
    }

    private fun deletePoolsTesting() {
        CoroutineScope(Dispatchers.IO).launch {
            for(i in 11..20) {
                container.poolRepository.deletePoolById("id$i")
            }
        }
    }

}