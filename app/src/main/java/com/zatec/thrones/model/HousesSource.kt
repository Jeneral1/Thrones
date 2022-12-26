package com.zatec.thrones.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zatec.thrones.network.RetrofitClient
import retrofit2.HttpException
import java.io.IOException

class HousesSource: PagingSource<Int, House>() {
    override fun getRefreshKey(state: PagingState<Int, House>): Int? {
        return state.anchorPosition
    }

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