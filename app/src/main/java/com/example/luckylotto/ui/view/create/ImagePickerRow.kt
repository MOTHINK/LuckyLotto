package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.luckylotto.ui.viewmodel.MainViewModel

@Composable
fun ImagePickerRow(mainViewModel: MainViewModel, poolImageValue: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                LabelText("Background image")
            }

            var selectedImageIndex by remember { mutableIntStateOf(0) }

            HorizontalImagePicker(
                mainViewModel,
                selectedImageIndex,
                selectedImageIndexValue = { value ->
                    selectedImageIndex = value
                },
                poolImageValue = { value ->
                    poolImageValue(value)

                }
            )
        }
    }
}