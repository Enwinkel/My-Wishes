package com.stasst.mywishes.ui.wishes_list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stasst.mywishes.data.Wish

@Composable
fun WishCard(
    wish: Wish,
    onEvent: (WishListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 5.dp).weight(1f),
                text = wish.wishText,
                fontSize = 20.sp
            )

            Checkbox(
                checked = wish.isFulfilled,
                onCheckedChange = { isChecked ->
                    onEvent(WishListEvent.OnDoneChange(wish, isChecked))
                }
            )

        }
    }
}