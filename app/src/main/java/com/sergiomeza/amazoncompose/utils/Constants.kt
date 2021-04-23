package com.sergiomeza.amazoncompose.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val HOME_ROUTE = "HomeScreen"
    const val ACCOUNT_ROUTE = "AccountScreen"
    const val CART_SCREEN = "CartScreen"
    const val SETTINGS_SCREEN = "SettingsScreen"

    enum class SectionType {
        GRID2, GRID3, ORDERS, PRODUCT, DEPARTMENTS
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.datetime(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm")
    return formatter.format(this)
}