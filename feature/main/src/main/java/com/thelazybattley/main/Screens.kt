package com.thelazybattley.main

sealed class Screens(
    val route: String
) {

    data object MainScreen : Screens(route = "main")

    data object MapScreen : Screens(route = "map")

}
