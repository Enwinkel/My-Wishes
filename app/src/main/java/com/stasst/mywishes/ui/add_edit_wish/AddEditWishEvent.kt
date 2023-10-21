package com.stasst.mywishes.ui.add_edit_wish

sealed class AddEditWishEvent{
    data class OnWishChange(val title: String): AddEditWishEvent()
    object OnSaveWishClick: AddEditWishEvent()
}