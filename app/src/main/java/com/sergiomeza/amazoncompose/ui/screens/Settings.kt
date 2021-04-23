package com.sergiomeza.amazoncompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sergiomeza.amazoncompose.R

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val items = stringArrayResource(id = R.array.settingsItems)
    Box(
        modifier = Modifier
            .padding(24.dp)
    ){
        LazyColumn {
            items(items){
                Text(
                    text = it,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.clickable {
                        // TODO: 23/04/2021
                    }.padding(8.dp)
                )
            }
        }
    }
}

