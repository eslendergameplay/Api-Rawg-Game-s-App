package com.example.jetpack10.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetpack10.model.GamesList
import com.example.jetpack10.repository.GamesRepository

class GamesDataSource (private val reposi:GamesRepository):PagingSource<Int,GamesList>() {
    override fun getRefreshKey(state: PagingState<Int, GamesList>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorpage = state.closestPageToPosition(anchorPosition)
            anchorpage?.prevKey?.plus(1) ?: anchorpage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesList> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = reposi.getGamesPaging(nextPageNumber,6)
            LoadResult.Page(data = response.results, prevKey = null, nextKey = if(response.results.isNotEmpty())nextPageNumber +1 else null)
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}