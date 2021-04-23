package com.sergiomeza.amazoncompose.ui.screens


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.sergiomeza.amazoncompose.utils.Constants

sealed class Screen(val route: String,
                    val icon: ImageVector?,
                    val iconFilled: ImageVector?
) {
    object Home : Screen(
        Constants.HOME_ROUTE, Icons.Outlined.Home, Icons.Filled.Home)
    object Account : Screen(
        Constants.ACCOUNT_ROUTE, Icons.Outlined.AccountBox, Icons.Filled.AccountBox)
    object Cart : Screen(
        Constants.CART_SCREEN, Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart)
    object Settings : Screen(
        Constants.SETTINGS_SCREEN, Icons.Outlined.Menu, Icons.Filled.Menu)

}