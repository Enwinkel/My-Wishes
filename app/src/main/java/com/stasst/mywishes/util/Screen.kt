package com.stasst.mywishes.util

sealed class Screen(
    val route: String
) {
    object WishListScreen : Screen(route = "wishes")
    object AddEditWishScreen : Screen(route = "add_edit_wishes")
}