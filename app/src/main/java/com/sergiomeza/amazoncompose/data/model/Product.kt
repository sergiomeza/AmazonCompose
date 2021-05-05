package com.sergiomeza.amazoncompose.data.model

data class Product(val productId: String, val name: String,
                   val image: String, val price: String,
                   val reviews: Int, val stars: Int,
                   val brand: String, val shipping: String,
                   val sponsored: Boolean, val featured: Boolean
)
