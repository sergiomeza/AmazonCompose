package com.sergiomeza.amazoncompose.ui.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergiomeza.amazoncompose.data.model.Product
import com.sergiomeza.amazoncompose.ui.screens.HomeImage
import com.sergiomeza.amazoncompose.ui.theme.imageBackground
import com.sergiomeza.amazoncompose.ui.theme.linkColor
import com.sergiomeza.amazoncompose.utils.Constants

@ExperimentalFoundationApi
@Composable
fun Section(
    sectionType: Constants.SectionType = Constants.SectionType.GRID2,
    data: List<Product> = arrayListOf(),
    title: String?, moreButton: String?){
    var isGrid = 0
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            when (sectionType) {
                Constants.SectionType.GRID2 -> isGrid = 2
                Constants.SectionType.GRID3 -> isGrid = 3
                Constants.SectionType.LIST -> {
                    data.forEach { 
                        ListItem(product = it){
                            // TODO: 3/05/2021
                        }
                    }
                }
                else -> Log.d("TAG", "Section: nadin")
            }
            if (isGrid > 0) {
                val rows = (data.size / isGrid) + (if (data.size % isGrid > 0) 1 else 0)
                for (r in 0 until rows) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        for (cell in 0 until isGrid) {
                            val i = (r * isGrid) + cell
                            if (i < data.size) {
                                ImageProduct(
                                    product = data[i],
                                    size = if (sectionType == Constants.SectionType.GRID3) 120.dp else 160.dp)
                            } else { break }
                        }
                    }
                }
            }
            moreButton?.let {
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.body2,
                    color = linkColor, 
                    modifier = Modifier.clickable {
                        // TODO: 23/04/2021  
                    }
                )
            }
        }
    }
}

@Composable
fun ListItem(product: Product, onProductClick: (Product) -> Unit){
    Box(
        modifier = Modifier
            .clickable {
                onProductClick(product)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                ImageProduct(
                    product = product,
                    size = 70.dp
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = product.name.toUpperCase(),
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "$${product.price.toBigDecimal()}",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Composable
fun ImageProduct(product: Product, size: Dp = 150.dp){
    Box(
        modifier = Modifier
            .background(imageBackground)
            .size(size = size)
    ) {
        HomeImage(source = product.image)
    }
}