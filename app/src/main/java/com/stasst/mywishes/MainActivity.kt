package com.stasst.mywishes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stasst.mywishes.ui.add_edit_wish.AddEditWishScreen
import com.stasst.mywishes.ui.theme.MyWishesTheme
import com.stasst.mywishes.ui.wishes_list.WishListScreen
import com.stasst.mywishes.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWishesTheme {
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
                                type = NavType.StringType
                                defaultValue = ""
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
