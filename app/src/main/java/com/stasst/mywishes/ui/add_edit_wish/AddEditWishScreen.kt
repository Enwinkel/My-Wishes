package com.stasst.mywishes.ui.add_edit_wish

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stasst.mytodo.add_edit_wish.AddEditWishViewModel
import com.stasst.mywishes.R
import com.stasst.mywishes.util.UiEvent

@Composable
fun AddEditWishScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditWishViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        if (viewModel.wish == null) {
            focusRequester.requestFocus()
        } else focusRequester.freeFocus()

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onEvent(AddEditWishEvent.OnSaveWishClick)
                },
                modifier = Modifier.imePadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            ) {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        onPopBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        if (viewModel.wish != null) {
                            viewModel.onEvent(AddEditWishEvent.OnDeleteWishClick(viewModel.wish!!))
                        } else {
                            onPopBackStack()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {

                TextField(
                    value = viewModel.wishText,
                    onValueChange = {
                        viewModel.onEvent(AddEditWishEvent.OnWishChange(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.goal))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
            }
        }
    }
}