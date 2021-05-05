package com.sergiomeza.amazoncompose.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.data.model.Product
import com.sergiomeza.amazoncompose.data.repository.Repository
import com.sergiomeza.amazoncompose.data.viewmodel.ScrollViewModel
import com.sergiomeza.amazoncompose.ui.theme.starColor
import com.sergiomeza.amazoncompose.ui.views.ImageProduct

@ExperimentalFoundationApi
@Composable
fun SearchResultScreen(
    navController: NavController,
    term: String?,
    scrollViewModel: ScrollViewModel
) {
    val listState = rememberLazyListState()
    scrollViewModel.updateScrollPosition(listState.firstVisibleItemIndex)

    Surface(
        modifier = Modifier.padding(6.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.results).toUpperCase(),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = listState
            ) {
                items(Repository.searchProducts){
                    SearchProduct(
                        product = it,
                        onProductClick = { }
                    )
                }
            }
        }
    }
}

@Composable
fun Filters(){

}

@Composable
fun SearchProduct(product: Product,
                  onProductClick: (Product) -> Unit){
    Box(
        modifier = Modifier
            .clickable {
                onProductClick(product)
            }
            .border(
                width = 0.2.dp,
                color = Color.LightGray
            )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                ImageProduct(
                    product = product,
                    size = 120.dp
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (product.sponsored){
                        Text(
                            text = stringResource(id = R.string.sponsored),
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.h4,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                    Text(
                        text = "By ${product.brand}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Stars(stars = product.stars)
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "${product.reviews.toBigDecimal()}",
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Text(
                        text = "$${product.price.toBigDecimal()}",
                        style = MaterialTheme.typography.h3,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                    Text(
                        text = product.shipping,
                        style = MaterialTheme.typography.caption,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun Stars(stars: Int){
    Row(modifier = Modifier) {
        for (i in 1..5) {
            var icon = Icons.Default.Star
            if (stars < i){
                icon = Icons.Default.StarBorder
            }
            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.rating),
                tint = starColor,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
