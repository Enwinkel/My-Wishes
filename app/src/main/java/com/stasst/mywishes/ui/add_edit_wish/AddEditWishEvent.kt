package com.stasst.mywishes.ui.add_edit_wish

import com.stasst.mywishes.data.Wish
import com.stasst.mywishes.ui.wishes_list.WishListEvent

sealed class AddEditWishEvent{
    data class OnWishChange(val title: String): AddEditWishEvent()
    data class OnDeleteWishClick(val wish: Wish): AddEditWishEvent()
    object OnSaveWishClick: AddEditWishEvent()
}