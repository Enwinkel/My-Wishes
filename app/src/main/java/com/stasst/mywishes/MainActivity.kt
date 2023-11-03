package com.stasst.mywishes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stasst.mywishes.ui.add_edit_wish.AddEditWishScreen
import com.stasst.mywishes.ui.theme.AppTheme
import com.stasst.mywishes.ui.wishes_list.WishListScreen
import com.stasst.mywishes.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController  = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.WishListScreen.route
                ){
                    composable(Screen.WishListScreen.route) {
                        WishListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        Screen.AddEditWishScreen.route + "?wishId={wishId}",
                        arguments = listOf(
                            navArgument(name = "wishId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        AddEditWishScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedItem: ImageVector,
    val selectedItem: ImageVector
)
