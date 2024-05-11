package com.dicoding.jetpackcomposebmw.ui.screen.detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetpackcomposebmw.data.di.Injec
import com.dicoding.jetpackcomposebmw.ui.common.ViewModelFactory
import com.dicoding.jetpackcomposebmw.ui.component.ItemDetail
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.dicoding.jetpackcomposebmw.ui.common.Result

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    BMWId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injec.provideRepository())
    ),
    navigateBack: () -> Unit,
    navigateToFavorite: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.background
    val isDark = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = !isDark
        )
    }

    viewModel.result.collectAsState(initial = Result.Loading).value.let { result ->
        when (result) {
            is Result.Loading -> {
                viewModel.getFavoriteBMWById(BMWId)
            }
            is Result.Success -> {
                val favoriteBMW = result.data
                val bmw = favoriteBMW.bmw
                DetailContent(
                    modifier = modifier,
                    id = bmw.id,
                    name = bmw.name,
                    description = bmw.description,
                    color = bmw.color,
                    type = bmw.type,
                    image = bmw.photoId,
                    highlight = favoriteBMW.favorite,
                    onBackClick = navigateBack,
                    onUpdateFavorite = {
                        viewModel.updateFavorite(BMWId)
                        navigateToFavorite()
                    }
                )
            }
            is Result.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    description: String,
    color: String,
    type: String,
    image: Int,
    highlight: Boolean,
    onBackClick: () -> Unit,
    onUpdateFavorite: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        ItemDetail(
            modifier = modifier,
            id = id,
            name = name,
            description = description,
            color = color,
            type = type,
            image = image,
            highlight = highlight,
            onBackClick = onBackClick,
            onUpdateFavorite = { onUpdateFavorite(id) }
        )
    }
}