package com.zatec.thrones.viewModel

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zatec.thrones.model.House
import com.zatec.thrones.model.HousesSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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