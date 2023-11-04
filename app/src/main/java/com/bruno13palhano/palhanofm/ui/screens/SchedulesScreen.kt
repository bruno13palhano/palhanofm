package com.bruno13palhano.palhanofm.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SchedulesScreen() {
    SchedulesContent()
}

@Composable
fun SchedulesContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Schedules Screen")
    }
}