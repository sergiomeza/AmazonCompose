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
import com.sergiomeza.amazoncompose.data.model.Product
import com.sergiomeza.amazoncompose.ui.views.Section
import com.sergiomeza.amazoncompose.utils.Constants
import dev.chrisbanes.accompanist.coil.CoilImage

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val itemsImg = listOf("https://m.media-amazon.com/images/I/51AZ5MW5WmL._SR1242,450_.jpg",
        "https://m.media-amazon.com/images/I/511S-MJC8rL._SR1242,450_.jpg")
    val babyProducts = listOf(
        Product(
            name = "Test product",
            image = "https://m.media-amazon.com/images/I/81+ev0VHJHL._AC_SY400_.jpg",
            productId = ""
        ),
        Product(
            name = "Test product 2",
            image = "https://m.media-amazon.com/images/I/91UIreBLyKL._AC_SY400_.jpg",
            productId = ""
        ),
        Product(
            name = "Test product 3",
            image = "https://m.media-amazon.com/images/I/71wNWtNb0sL._AC_SY400_.jpg",
            productId = ""
        ),
        Product(
            name = "Test product 4",
            image = "https://m.media-amazon.com/images/I/71sCcP2ZIAL._AC_SY400_.jpg",
            productId = ""
        )
    )
    val listState = rememberLazyListState()
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier =
            Modifier
                .padding(bottom = 50.dp)
                .fillMaxSize(),
            state = listState
        ) {
            items(itemsImg) {
                HomeImage(source = it)
            }
            item {
                Section(
                    title = stringResource(id = R.string.section_must_have_baby),
                    moreButton = stringResource(id = R.string.button_shop_now),
                    data = babyProducts
                )
            }
            item {
                Section(
                    title = stringResource(id = R.string.section_stuffed_under_10),
                    moreButton = stringResource(id = R.string.button_see_more),
                    data = babyProducts, sectionType = Constants.SectionType.GRID3
                )
            }
            item {
                Section(
                    title = stringResource(id = R.string.section_discount_electronics),
                    moreButton = stringResource(id = R.string.button_see_more),
                    data = babyProducts
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