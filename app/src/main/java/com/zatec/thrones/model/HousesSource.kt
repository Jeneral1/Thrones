package com.zatec.thrones.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zatec.thrones.network.RetrofitClient
import retrofit2.HttpException
import java.io.IOException

/**
 * Used to implement Pagination for the GetHousesScreen by inheriting [PagingSource] which is used to
 * load pages from the source data
 * */
class HousesSource: PagingSource<Int, House>() {
    /**
     * overrides a function to get a Key which is used to load the next [PagingSource]
     *
     * @param state an instance of [PagingState] which is the snapshot state of the Paging system
     * */
    override fun getRefreshKey(state: PagingState<Int, House>): Int? {
        return state.anchorPosition
    }

    /**
     * overrides a function that implements the async load for the source data and the key for loading the next page of data
     *
     * @param params LoadParams that holds the Key for the page of the data to be loaded
     *
     * @return Result from the async task as well as the key for the next page.
     * @exception IOException thrown when page cannot be loaded... example is when network is off.
     * @exception HttpException thrown when http call returns a response code which is not 2xx. errors caught by the calling class
     *
     * */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, House> {
        return try {
            val page = params.key?: 1
            val houseList = RetrofitClient.apiService.getHousesList(page = page)

            LoadResult.Page(
                data = houseList,
                prevKey = if(page==1) null else page-1,
                nextKey = if(houseList.isEmpty()) null else page +1
            )
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}