package com.bruno13palhano.palhanofm.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bruno13palhano.core.model.Schedule
import com.bruno13palhano.palhanofm.ui.components.ExpandedItem
import com.bruno13palhano.palhanofm.ui.screens.viewmodels.SchedulesViewModel

@Composable
fun SchedulesScreen(
    viewModel: SchedulesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) { viewModel.synchronize() }

    val schedules by viewModel.schedules.collectAsStateWithLifecycle()

    SchedulesContent(schedules = schedules)
}

@Composable
fun SchedulesContent(
    schedules: List<Schedule>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp)
    ) {
        items(items = schedules, key = { schedule -> schedule.id }) { schedule ->
            ExpandedItem(
                modifier = Modifier.padding(vertical = 4.dp),
                title = schedule.title,
                broadcaster = schedule.broadcaster,
                startTime = schedule.startTime,
                endTime = schedule.endTime
            )
        }
    }
}