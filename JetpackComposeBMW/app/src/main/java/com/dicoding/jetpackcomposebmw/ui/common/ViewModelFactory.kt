package com.dicoding.jetpackcomposebmw.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetpackcomposebmw.data.repository.BMWRepository
import com.dicoding.jetpackcomposebmw.ui.screen.detail.DetailViewModel
import com.dicoding.jetpackcomposebmw.ui.screen.favorite.FavoriteViewModel
import com.dicoding.jetpackcomposebmw.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BMWRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repository) as T
            DetailViewModel::class.java -> DetailViewModel(repository) as T
            FavoriteViewModel::class.java -> FavoriteViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
}