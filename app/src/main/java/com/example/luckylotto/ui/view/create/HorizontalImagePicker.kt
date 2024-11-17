package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun HorizontalImagePicker(mainViewModel: MainViewModel, selectedImageIndex: Int, selectedImageIndexValue: (Int) -> Unit, poolImageValue: (String) -> Unit) {
    LazyRow(modifier = Modifier.fillMaxWidth().height(120.dp).padding(20.dp, 0.dp).clip(CircleShape).background(Color.White)) {
        items(mainViewModel.imageList.size) {
            SelectedImage(
                mainViewModel.imageList[it],
                it,
                selectedImageIndex,
                indexChange = { value ->
                    selectedImageIndexValue(value)
                },
                selectedPoolImage = { value ->
                    poolImageValue(mainViewModel.imageList[value])
                }
            )
        }
    }
}