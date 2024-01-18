package com.chirag047.InstaSave.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chirag047.InstaSave.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val apiRepository: ApiRepository) : ViewModel() {

    val data = apiRepository.data

    fun getVideo(url: String) {
        viewModelScope.launch {
            apiRepository.getVideo(url)
        }
    }
}