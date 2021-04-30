package com.sergiomeza.amazoncompose.data.model

data class Search(val isRecent: Boolean,
                  val text: String,
                  val uri: String? = "")
