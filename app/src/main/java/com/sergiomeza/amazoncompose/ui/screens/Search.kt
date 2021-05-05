package com.sergiomeza.amazoncompose.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.data.model.Search
import com.sergiomeza.amazoncompose.data.viewmodel.SearchViewModel

@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    onRemoveSearch: (Search) -> Unit,
    onSearchClicked: (String) -> Unit,
    viewModel: SearchViewModel
) {
    val searchState by viewModel.searchItems.observeAsState()
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ){
        if (searchState?.isEmpty() == true){
            EmptyPage(text = stringResource(id = R.string.empty_search))
        } else {
            LazyColumn {
                items(searchState!!) {
                    SearchItem(
                        search = it,
                        onRemoveSearch = { sr ->
                            onRemoveSearch(sr)
                        },
                        onSearchClicked = { searchTerm ->
                            onSearchClicked(searchTerm)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyPage(text: String){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.amazon_logo),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            alpha = 0.4f
        )
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
fun SearchItem(search: Search,
               onRemoveSearch: (Search) -> Unit,
               onSearchClicked: (String) -> Unit){
    Column(
        modifier = Modifier.clickable {
            onSearchClicked(search.text)
        }
    ) {
        Row(modifier = Modifier
            .padding(vertical = 6.dp)
        ) {
            if(search.isRecent) {
                IconButton(
                    modifier = Modifier.then(Modifier.size(24.dp)),
                    onClick = {
                        onRemoveSearch(search)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.camera),
                        tint = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
            Box(Modifier.weight(1f)){
                Text(
                    text = search.text,
                    modifier = Modifier,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = stringResource(R.string.camera),
                tint = Color.LightGray
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}