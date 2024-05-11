package com.dicoding.jetpackcomposebmw.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.dicoding.jetpackcomposebmw.R
import com.dicoding.jetpackcomposebmw.ui.nav.NavItem
import com.dicoding.jetpackcomposebmw.ui.nav.Screen
import com.dicoding.jetpackcomposebmw.ui.screen.detail.DetailScreen
import com.dicoding.jetpackcomposebmw.ui.screen.favorite.FavoriteScreen
import com.dicoding.jetpackcomposebmw.ui.screen.home.HomeScreen
import com.dicoding.jetpackcomposebmw.ui.screen.profile.ProfileScreen

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMWApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier,
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        navigateToDetail = { BMWId ->
                            navController.navigate(Screen.Detail.createRoute(BMWId))
                        }
                    )
                }
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("BMWId") { type = NavType.IntType })
                ) {
                    DetailScreen(
                        BMWId = it.arguments?.getInt("BMWId") ?: 0,
                        navigateBack = {
                            navController.navigateUp()
                        },
                        navigateToFavorite = {
                            navController.popBackStack()
                            navController.navigate(Screen.Favorite.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
                composable(Screen.Favorite.route) {
                    FavoriteScreen(navigateToDetail = { BMWId ->
                        navController.navigate(Screen.Detail.createRoute(BMWId))
                    })
                }
            }
        }
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavItem(
            title = stringResource(R.string.favorite),
            icon = Icons.Default.Favorite,
            screen = Screen.Favorite
        ),
        NavItem(
            title = stringResource(R.string.profile),
            icon = Icons.Default.Person,
            screen = Screen.Profile
        ),
    )
    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.tertiary,
                    selectedTextColor = MaterialTheme.colorScheme.background,
                    indicatorColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = MaterialTheme.colorScheme.background,
                    unselectedTextColor = MaterialTheme.colorScheme.background,
                ),
                alwaysShowLabel = false,
            )
        }
    }
}
