package com.chirag047.InstaSave.repository

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chirag047.InstaSave.Common.API_HOST
import com.chirag047.InstaSave.Common.API_KEY
import com.chirag047.InstaSave.api.InstaApi
import com.chirag047.InstaSave.models.InstaModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ApiRepository @Inject constructor(val instaApi: InstaApi) {

    private val _data = MutableStateFlow<InstaModel>(InstaModel("", "", "", "", ""))
    val data: StateFlow<InstaModel>
        get() = _data

    suspend fun getVideo(url: String) {
        val result = instaApi.getVideo(url, API_KEY, API_HOST)

        if (result.isSuccessful && result.body() != null) {
            _data.emit(result.body()!!)
        }
    }

}