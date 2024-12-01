package com.example.luckylotto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.database.AppContainer
import com.example.luckylotto.data.database.AppDataContainer
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.ui.navigation.AppNavigation
import com.example.luckylotto.ui.theme.LuckyLottoTheme
import com.example.luckylotto.ui.view.components.NewCustomBottomBar
import com.example.luckylotto.ui.view.create.CreatingPoolSnackBarMessage
import com.example.luckylotto.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var container: AppContainer
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
        "StateFlowValueCalledInComposition"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container = AppDataContainer(this)
        mainViewModel = MainViewModel(container.poolRepository,container.ticketRepository)

        if(mainViewModel.user.value == null) {
            mainViewModel.setFirebaseUser(FirebaseAuthentication.instance.getFirebaseCurrentUser())
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            createPoolsTesting()
//            deletePoolsTesting()
//        }

        installSplashScreen()
        setContent {
            LuckyLottoTheme {
                Scaffold(
                    bottomBar = { if(mainViewModel.user.collectAsState().value != null) NewCustomBottomBar(mainViewModel,Modifier) },
                    snackbarHost = { CreatingPoolSnackBarMessage(mainViewModel) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        AppNavigation.instance.InitializeNavigation(mainViewModel)
                        if(FirebaseAuthentication.instance.getFirebaseCurrentUser() != null) {
                            AppNavigation.instance.appNavigation()[1]()
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private suspend fun createPoolsTesting() {
        for (i in 11..20) {
            container.poolRepository.insertPool(Pool("id$i",FirebaseAuthentication.instance.getFirebaseCurrentUser()?.uid.toString(),"",10.0*i,1000*i,50*i,System.currentTimeMillis(),System.currentTimeMillis()+(60000L*60),mainViewModel.imageList[i-10]))
        }
    }

    private suspend fun deletePoolsTesting() {
        for(i in 11..20) {
            container.poolRepository.deletePoolById("id$i")
        }
    }

}