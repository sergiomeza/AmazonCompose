package com.sergiomeza.amazoncompose.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiomeza.amazoncompose.data.model.Search
import com.sergiomeza.amazoncompose.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll

class SearchViewModel: ViewModel() {
    private val _searchItems = MutableLiveData(arrayListOf<Search>())
    val searchItems: LiveData<ArrayList<Search>>
        get() = _searchItems

    // event: addItem
    fun addItem(item: Search) {
        _searchItems.value?.add(item)
    }

    // event: removeItem
    fun removeSearch(item: Search) {
        _searchItems.value?.also {
            it.remove(item)
        }
    }
}