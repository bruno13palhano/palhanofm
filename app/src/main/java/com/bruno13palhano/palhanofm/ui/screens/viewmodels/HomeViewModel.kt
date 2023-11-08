package com.bruno13palhano.palhanofm.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.repository.di.SponsorsRep
import com.bruno13palhano.core.repository.sponsors.SponsorsDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @SponsorsRep private val repository: SponsorsDataRepository<Sponsor>
): ViewModel() {
    val sponsorsImagesUrl = repository.getAll()
        .map { it.map { sponsor -> sponsor.imageUrl } }
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