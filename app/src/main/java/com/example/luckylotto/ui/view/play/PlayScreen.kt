package com.example.luckylotto.ui.view.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun PlayScreen(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TopNavBarSearchPoolCard(mainViewModel)
                Spacer(modifier = Modifier.height(5.dp))
                PoolCardList(mainViewModel)
            }
        }
    }
}

@Composable
fun PoolCardList(mainViewModel: MainViewModel) {
    val pools by mainViewModel.pools.collectAsState()
    val poolSearchText by mainViewModel.poolSearchText.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        val filteredPools = pools.filter { it.poolId.startsWith(poolSearchText) && it.closeTime >= System.currentTimeMillis() && !it.isPrivate}.toList()
        items(filteredPools.size) {
            PoolCard(filteredPools[it])
        }
    }
}
