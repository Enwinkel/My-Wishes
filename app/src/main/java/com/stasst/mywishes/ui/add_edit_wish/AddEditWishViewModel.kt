package com.stasst.mytodo.add_edit_wish

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stasst.mywishes.ui.add_edit_wish.AddEditWishEvent
import com.stasst.mywishes.data.Wish
import com.stasst.mywishes.data.WishRepository
import com.stasst.mywishes.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditWishViewModel @Inject constructor(
    private val repository: WishRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var wish by mutableStateOf<Wish?>(null)
        private set
    var wishText by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val wishId = savedStateHandle.get<Int>("wishId")!!
        if (wishId != -1) {
            viewModelScope.launch {
                repository.getWishById(wishId)?.let { wish ->
                    wishText = wish.wishText
                    this@AddEditWishViewModel.wish = wish
                }
            }
        }
    }

    fun onEvent(event: AddEditWishEvent) {
        when (event) {
            is AddEditWishEvent.OnWishChange -> {
                wishText = event.title
            }
            is AddEditWishEvent.OnDeleteWishClick -> {
                viewModelScope.launch {
                    repository.deleteWish(event.wish)
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is AddEditWishEvent.OnSaveWishClick -> {
                viewModelScope.launch {
                    if(wishText != "") {
                        val newWish = Wish(
                            wishText = wishText,
                            isFulfilled = wish?.isFulfilled ?: false,
                            id = wish?.id
                        )
                        repository.insertWish(
                            newWish
                        )
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}