package com.stasst.mywishes.ui.wishes_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Tapas
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.Tapas
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stasst.mywishes.R
import com.stasst.mywishes.util.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WishListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: WishListViewModel = hiltViewModel()
) {
    val wishes = viewModel.wishes.collectAsState(initial = emptyList())
    var wishesOnPage = wishes.value
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var fabVisible by remember { mutableStateOf(true) }

    val tabItems = listOf(
        TabItem(
            title = stringResource(id = R.string.goals),
            unselectedIcon = Icons.Outlined.Tapas,
            selectedIcon = Icons.Filled.Tapas
        ),
        TabItem(
            title = stringResource(id = R.string.completed),
            unselectedIcon = Icons.Outlined.DoneAll,
            selectedIcon = Icons.Filled.DoneAll
        ),
    )

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTabIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (index) {
                        0 -> {
                            fabVisible = true
                            wishesOnPage = wishes.value.filter { !(it.isFulfilled) }
                        }
                        1 -> {
                            fabVisible = false
                            wishesOnPage = wishes.value.filter { it.isFulfilled }
                        }
                    }
                    items(wishesOnPage) { wish ->
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedVisibility(
                    visible = fabVisible,
                    enter = scaleIn(),
                    exit = scaleOut(),
                    modifier = Modifier
                        .padding(16.dp)

                ) {
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(WishListEvent.OnAddWishClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)