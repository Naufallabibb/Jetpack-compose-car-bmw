package com.dicoding.jetpackcomposebmw.ui.nav

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("home/{BMWId}") {
        fun createRoute(BMWId: Int) = "home/$BMWId"
    }
}