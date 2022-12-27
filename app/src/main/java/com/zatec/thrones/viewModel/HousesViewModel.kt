package com.zatec.thrones.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zatec.thrones.model.House
import com.zatec.thrones.model.HousesSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HousesViewModel: ViewModel() {
    var firstScrollIndex = mutableStateOf(0)
    var firstScrollOffset = mutableStateOf(0)
    private val  _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    var house: Flow<PagingData<House>> = Pager(PagingConfig(pageSize = 20)){
        HousesSource()
    }.flow.cachedIn(viewModelScope)

    fun refresh() = viewModelScope.launch {
        _isRefreshing.update { true }
        delay(2000)
        _isRefreshing.update { false }
        house = Pager(PagingConfig(pageSize = 20)){
            HousesSource()
        }.flow.cachedIn(viewModelScope)
    }
}