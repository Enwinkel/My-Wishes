package com.stasst.mywishes.ui.wishes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stasst.mywishes.data.WishRepository
import com.stasst.mywishes.util.Screen
import com.stasst.mywishes.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val repository: WishRepository
): ViewModel() {

    val wishes = repository.getWishes()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: WishListEvent) {
        when(event) {
            is WishListEvent.OnWishClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.AddEditWishScreen.route + "?wishId=${event.wish.wishText}"))
            }
            is WishListEvent.OnAddWishClick ->{
                sendUiEvent(UiEvent.Navigate(Screen.AddEditWishScreen.route))
            }
            is WishListEvent.OnDeleteWishClick -> {
                viewModelScope.launch {
                    repository.deleteWish(event.wish)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Todo deleted"
                    ))
                }
            }
            is WishListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertWish(
                        event.wish.copy(
                            isFulfilled = event.isFulfilled
                        )
                    )
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