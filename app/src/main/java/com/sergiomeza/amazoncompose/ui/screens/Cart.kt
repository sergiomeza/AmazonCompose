package com.sergiomeza.amazoncompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.sergiomeza.amazoncompose.R

@Composable
fun CartScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()){
        EmptyPage(text = stringResource(id = R.string.cart))
    }
}

