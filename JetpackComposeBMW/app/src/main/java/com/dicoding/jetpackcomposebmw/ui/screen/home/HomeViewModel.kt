package com.dicoding.jetpackcomposebmw.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpackcomposebmw.data.model.FavoriteBMW
import com.dicoding.jetpackcomposebmw.data.repository.BMWRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.dicoding.jetpackcomposebmw.ui.common.Result

class HomeViewModel(
    private val repository: BMWRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<List<FavoriteBMW>>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _noMatch = MutableStateFlow(false)
    val noMatch = _noMatch.asStateFlow()

    fun getAllBMW() {
        viewModelScope.launch {
            repository.getAllFavoriteBMW()
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
                }
                .collect { favoriteBMW ->
                    _result.value = Result.Success(favoriteBMW)
                }
        }
    }

    fun searchBMW(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.searchBMW(query)
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
                }
                .collect { favoriteBMW ->
                    if (favoriteBMW.isEmpty()) {
                        _noMatch.value = true
                        _result.value = Result.Success(emptyList())
                    } else {
                        _noMatch.value = false
                        _result.value = Result.Success(favoriteBMW)
                    }
                }
        }
    }
}