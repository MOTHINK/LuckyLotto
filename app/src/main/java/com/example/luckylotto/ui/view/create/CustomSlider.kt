package com.example.luckylotto.ui.view.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.luckylotto.ui.theme.CustomBlue
import kotlin.math.round


@Composable
fun CustomSlider(sliderValues: List<Int>, value: String, result: (Int) -> Unit) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            modifier = Modifier.padding(20.dp,0.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = CustomBlue,
                activeTrackColor = CustomBlue,
                inactiveTrackColor = Color.White,
            ),
            steps = sliderValues.size - 2,
            valueRange = 0f..(sliderValues.size-1).toFloat()
        )
        result(sliderValues[round(sliderPosition).toInt()])
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(5.dp)
                ),
            text = " " + sliderValues[round(sliderPosition).toInt()].toString() + " " + value +" ",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}