package com.sergiomeza.amazoncompose.data.repository

import com.sergiomeza.amazoncompose.data.model.Product
import com.sergiomeza.amazoncompose.data.model.Search

class Repository {
    companion object {
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

        val searchItems = listOf(
            Search(isRecent = false, text = "Search Item"),
            Search(isRecent = true, text = "Search Item 2"),
            Search(isRecent = true, text = "Search Item 3")
        )
    }
}