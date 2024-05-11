package com.dicoding.jetpackcomposebmw.data.di

import com.dicoding.jetpackcomposebmw.data.repository.BMWRepository

object Injec {
    fun provideRepository(): BMWRepository {
        return BMWRepository.getInstance()
    }
}