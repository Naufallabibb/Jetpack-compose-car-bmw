package com.dicoding.jetpackcomposebmw.ui.screen.favorite

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetpackcomposebmw.R
import com.dicoding.jetpackcomposebmw.data.di.Injec
import com.dicoding.jetpackcomposebmw.data.model.FavoriteBMW
import com.dicoding.jetpackcomposebmw.ui.common.ViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.dicoding.jetpackcomposebmw.ui.common.Result
import com.dicoding.jetpackcomposebmw.ui.component.FavoriteItem

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injec.provideRepository() )
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.primary
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
                viewModel.getFavoriteBMW()
            }
            is Result.Success -> {
                FavoriteContent(
                    modifier = modifier,
                    favoriteBMW = result.data,
                    noMatch = viewModel.noMatch.value,
                    navigateToDetail = navigateToDetail,
                )
            }
            is Result.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    favoriteBMW: List<FavoriteBMW>,
    noMatch: Boolean,
    navigateToDetail: (Int) -> Unit,
) {
    if (noMatch) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(R.string.no_data))
        }
    } else {
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = favoriteBMW,
                    key = { it.bmw.id }
                ) {data ->
                    FavoriteItem(
                        image = data.bmw.photoId,
                        name = data.bmw.name,
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(data.bmw.id.toInt())
                            }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }
}