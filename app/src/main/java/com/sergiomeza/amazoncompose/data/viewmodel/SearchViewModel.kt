package com.sergiomeza.amazoncompose.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiomeza.amazoncompose.data.model.Search
import com.sergiomeza.amazoncompose.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll

class SearchViewModel: ViewModel() {
    private val _searchItems: MutableStateFlow<ArrayList<Search>> =
        MutableStateFlow(arrayListOf())
    val searchItems: StateFlow<ArrayList<Search>> get() = _searchItems
    init {
        _searchItems.value.addAll(Repository.searchItems)
    }

    // event: addItem
    fun addItem(item: Search) {
        _searchItems.value.add(item)
        _searchItems.value = _searchItems.value
    }

    // event: removeItem
    fun removeSearch(item: Search) {
        _searchItems.value.also {
            it.remove(item)
        }
    }
}