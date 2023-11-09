package com.bruno13palhano.palhanofm.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.core.model.Schedule
import com.bruno13palhano.core.repository.di.SchedulesRep
import com.bruno13palhano.core.repository.schedules.SchedulesDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulesViewModel @Inject constructor(
    @SchedulesRep private val repository: SchedulesDataRepository<Schedule>
) : ViewModel() {
    val schedules = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun synchronize() {
        viewModelScope.launch {
            repository.synchronize()
        }
    }
}