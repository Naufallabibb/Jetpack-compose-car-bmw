package com.dicoding.jetpackcomposebmw.data.repository

import com.dicoding.jetpackcomposebmw.data.model.BMWData
import com.dicoding.jetpackcomposebmw.data.model.FavoriteBMW
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BMWRepository {
    private val favoriteBMW = mutableListOf<FavoriteBMW>()

    init {
        if (favoriteBMW.isEmpty()) {
            BMWData.bmw.forEach {
                favoriteBMW.add(FavoriteBMW(it))
            }
        }
    }

    fun getAllFavoriteBMW(): Flow<List<FavoriteBMW>> {
        return flow {
            emit(favoriteBMW)
        }
    }

    fun getFavoriteBMWById(BMWId: Int): FavoriteBMW {
        return favoriteBMW.first {
            it.bmw.id == BMWId.toString()
        }
    }

    fun updateFavoriteBMW(BMWId: Int): Flow<Boolean> {
        val index = favoriteBMW.indexOfFirst { it.bmw.id == BMWId.toString() }
        val result = if (index >= 0) {
            val bmw = favoriteBMW[index]
            bmw.favorite = !bmw.favorite
            true
        } else {
            false
        }
        return flow {
            emit(result)
        }
    }

    fun getFavoriteBMW(): Flow<List<FavoriteBMW>> {
        return getAllFavoriteBMW()
            .map {
                it.filter { bmw ->
                    bmw.favorite
                }
            }
    }

    fun searchBMW(query: String): Flow<List<FavoriteBMW>> {
        return getAllFavoriteBMW().map {
            it.filter { bmw ->
                bmw.bmw.name.contains(query, true)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: BMWRepository? = null

        fun getInstance(): BMWRepository =
            instance ?: synchronized(this) {
                BMWRepository().apply {
                    instance = this
                }
            }
    }
}
