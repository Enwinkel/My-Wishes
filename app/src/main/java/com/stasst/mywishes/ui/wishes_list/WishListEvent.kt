package com.stasst.mywishes.ui.wishes_list

import com.stasst.mywishes.data.Wish

sealed class WishListEvent {
    data class OnWishClick(val wish: Wish): WishListEvent()
    object OnAddWishClick: WishListEvent()
    data class OnDeleteWishClick(val wish: Wish): WishListEvent()
    data class OnDoneChange(val wish: Wish, val isFulfilled: Boolean): WishListEvent()
}