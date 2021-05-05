package com.sergiomeza.amazoncompose.data.repository

import com.sergiomeza.amazoncompose.data.model.Product
import com.sergiomeza.amazoncompose.data.model.Search

class Repository {
    companion object {
        val itemsImg = listOf("https://m.media-amazon.com/images/I/51AZ5MW5WmL._SR1242,450_.jpg")
        val babyProducts = listOf(
            Product(
                name = "Test product",
                image = "https://m.media-amazon.com/images/I/81+ev0VHJHL._AC_SY400_.jpg",
                productId = "",
                price = "68.99", brand = "PlayStation", featured = false,
                reviews = 241, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 2",
                image = "https://m.media-amazon.com/images/I/91UIreBLyKL._AC_SY400_.jpg",
                productId = "",
                price = "98.19",
                brand = "Sony", featured = false,
                reviews = 241, shipping = "10.22 Shipping", sponsored = true,
                stars = 2
            ),
            Product(
                name = "Test product 3",
                image = "https://m.media-amazon.com/images/I/71wNWtNb0sL._AC_SY400_.jpg",
                productId = "",
                price = "159.9",
                brand = "Xiaomi", featured = true,
                reviews = 10, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 4 so long that we need a linebreak to be able to show",
                image = "https://m.media-amazon.com/images/I/71sCcP2ZIAL._AC_SY400_.jpg",
                productId = "",
                price = "10.15",
                brand = "Apple", featured = false,
                reviews = 12541, shipping = "Free Shipping", sponsored = false,
                stars = 4
            )
        )
        val searchProducts = listOf(
            Product(
                name = "Test product",
                image = "https://m.media-amazon.com/images/I/81+ev0VHJHL._AC_SY400_.jpg",
                productId = "",
                price = "68.99", brand = "PlayStation", featured = false,
                reviews = 241, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 2",
                image = "https://m.media-amazon.com/images/I/91UIreBLyKL._AC_SY400_.jpg",
                productId = "",
                price = "98.19",
                brand = "Sony", featured = false,
                reviews = 241, shipping = "10.22 Shipping", sponsored = true,
                stars = 2
            ),
            Product(
                name = "Test product 3",
                image = "https://m.media-amazon.com/images/I/71wNWtNb0sL._AC_SY400_.jpg",
                productId = "",
                price = "159.9",
                brand = "Xiaomi", featured = true,
                reviews = 10, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 4 so long that we need a linebreak to be able to show",
                image = "https://m.media-amazon.com/images/I/71sCcP2ZIAL._AC_SY400_.jpg",
                productId = "",
                price = "10.15",
                brand = "Apple", featured = false,
                reviews = 12541, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 3",
                image = "https://m.media-amazon.com/images/I/71wNWtNb0sL._AC_SY400_.jpg",
                productId = "",
                price = "159.9",
                brand = "Xiaomi", featured = true,
                reviews = 10, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 4 so long that we need a linebreak to be able to show",
                image = "https://m.media-amazon.com/images/I/71sCcP2ZIAL._AC_SY400_.jpg",
                productId = "",
                price = "10.15",
                brand = "Apple", featured = false,
                reviews = 12541, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 3",
                image = "https://m.media-amazon.com/images/I/71wNWtNb0sL._AC_SY400_.jpg",
                productId = "",
                price = "159.9",
                brand = "Xiaomi", featured = true,
                reviews = 10, shipping = "Free Shipping", sponsored = false,
                stars = 4
            ),
            Product(
                name = "Test product 4 so long that we need a linebreak to be able to show",
                image = "https://m.media-amazon.com/images/I/71sCcP2ZIAL._AC_SY400_.jpg",
                productId = "",
                price = "10.15",
                brand = "Apple", featured = false,
                reviews = 12541, shipping = "Free Shipping", sponsored = false,
                stars = 4
            )
        )
    }
}