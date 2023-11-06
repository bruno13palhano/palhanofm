package com.bruno13palhano.palhanofm.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    val sponsorsImagesUrl = MutableStateFlow(
        listOf(
            "https://www.palhanofm.adcast.com.br/admin/assets/img/anunciantes/1819-2021-03-26.png",
            "https://www.palhanofm.adcast.com.br/admin/assets/img/anunciantes/1868-2023-10-05.jpg",
            "https://www.palhanofm.adcast.com.br/admin/assets/img/anunciantes/8344-2023-10-05.jpg",
            "https://www.palhanofm.adcast.com.br/admin/assets/img/anunciantes/7570-2023-10-05.png"
        )
    )
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}