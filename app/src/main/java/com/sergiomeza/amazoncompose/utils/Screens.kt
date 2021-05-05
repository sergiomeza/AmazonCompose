package com.sergiomeza.amazoncompose.ui.screens


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
    object Search : Screen(
        Constants.SEARCH_SCREEN, Icons.Outlined.Search, Icons.Filled.Search)
    object SearchResult : Screen(
        Constants.SEARCH_RESULT_SCREEN, Icons.Outlined.Search, Icons.Filled.Search)
}