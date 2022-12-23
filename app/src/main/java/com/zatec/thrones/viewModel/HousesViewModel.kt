package com.zatec.thrones.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zatec.thrones.model.House
import com.zatec.thrones.model.HousesSource
import kotlinx.coroutines.flow.Flow

class HousesViewModel: ViewModel() {
    val house: Flow<PagingData<House>> = Pager(PagingConfig(pageSize = 20)){
        HousesSource()
    }.flow.cachedIn(viewModelScope)
}