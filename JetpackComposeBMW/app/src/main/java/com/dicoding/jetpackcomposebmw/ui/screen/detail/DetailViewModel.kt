package com.dicoding.jetpackcomposebmw.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpackcomposebmw.data.model.FavoriteBMW
import com.dicoding.jetpackcomposebmw.data.repository.BMWRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.dicoding.jetpackcomposebmw.ui.common.Result

class DetailViewModel(
    private val repository: BMWRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<FavoriteBMW>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    fun getFavoriteBMWById(BMWId : Int) {
        viewModelScope.launch {
            _result.value = Result.Loading
            _result.value = Result.Success(repository.getFavoriteBMWById(BMWId))
        }
    }

    fun updateFavorite(BMWId : Int) {
        viewModelScope.launch {
            repository.updateFavoriteBMW(BMWId)
        }
    }
}