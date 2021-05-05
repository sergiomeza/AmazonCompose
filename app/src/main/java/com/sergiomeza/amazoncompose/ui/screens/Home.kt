package com.sergiomeza.amazoncompose.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.data.repository.Repository
import com.sergiomeza.amazoncompose.data.viewmodel.ScrollViewModel
import com.sergiomeza.amazoncompose.ui.views.Section
import com.sergiomeza.amazoncompose.utils.Constants
import dev.chrisbanes.accompanist.coil.CoilImage

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    scrollViewModel: ScrollViewModel
) {
    val listState = rememberLazyListState()
    scrollViewModel.updateScrollPosition(listState.firstVisibleItemIndex)
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            items(Repository.itemsImg) {
                HomeImage(source = it)
            }
            item {
                Section(
                    title = stringResource(id = R.string.section_must_have_baby),
                    moreButton = stringResource(id = R.string.button_see_more_manage),
                    data = Repository.babyProducts,
                    sectionType = Constants.SectionType.LIST
                )
            }
            item {
                Section(
                    title = stringResource(id = R.string.section_stuffed_under_10),
                    moreButton = stringResource(id = R.string.button_see_more),
                    data = Repository.babyProducts, sectionType = Constants.SectionType.GRID3
                )
            }
            item {
                Section(
                    title = stringResource(id = R.string.section_discount_electronics),
                    moreButton = stringResource(id = R.string.button_see_more),
                    data = Repository.babyProducts
                )
            }

        }
    }
}

@Composable
fun HomeImage(source: String){
    CoilImage(
        data = source,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        loading = {
            Box(Modifier.matchParentSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.amazon_logo),
                contentDescription = null
            )
        },
        modifier = Modifier.clickable {
            Log.d("TAG", "HomeBannerImage: Sisa $source ")
        }
    )  
}