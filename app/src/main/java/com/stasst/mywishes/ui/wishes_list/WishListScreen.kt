package com.stasst.mywishes.ui.wishes_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stasst.mywishes.ui.add_edit_wish.AddEditWishEvent
import com.stasst.mywishes.util.UiEvent

@Composable
fun WishListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: WishListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val wishes = viewModel.wishes.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(WishListEvent.OnAddWishClick)
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Save"
                )
            }
        }
    ) { padding ->
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                backgroundColor = Color.Yellow
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 5.dp
                        ),
                        text = "Хотелки",
                        fontSize = 24.sp
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(wishes.value) { wish ->
                    Box(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        WishCard(
                            wish = wish,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(WishListEvent.OnWishClick(wish))
                                }
                                .padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}