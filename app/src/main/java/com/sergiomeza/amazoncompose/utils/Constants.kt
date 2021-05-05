package com.sergiomeza.amazoncompose.utils

import android.annotation.SuppressLint
import com.sergiomeza.amazoncompose.ui.screens.Screen
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val HOME_ROUTE = "HomeScreen"
    const val ACCOUNT_ROUTE = "AccountScreen"
    const val CART_SCREEN = "CartScreen"
    const val SETTINGS_SCREEN = "SettingsScreen"
    const val SEARCH_SCREEN = "SearchScreen"
    const val SEARCH_RESULT_SCREEN = "SearchResultScreen"

    enum class SectionType {
        LIST, GRID2, GRID3, ORDERS, PRODUCT, DEPARTMENTS
    }

    enum class SearchState {
        SEARCHING,
        NORMAL
    }
    val navItems = listOf(
        Screen.Home,
        Screen.Account,
        Screen.Cart,
        Screen.Settings
    )
}

@SuppressLint("SimpleDateFormat")
fun Date.datetime(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm")
    return formatter.format(this)
}