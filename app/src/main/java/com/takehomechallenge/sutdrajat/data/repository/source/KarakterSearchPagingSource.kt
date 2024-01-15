package com.takehomechallenge.sutdrajat.data.repository.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.takehomechallenge.sutdrajat.data.remote.response.KarakterResponse
import com.takehomechallenge.sutdrajat.data.remote.response.ResultsItem
import com.takehomechallenge.sutdrajat.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KarakterSearchPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, ResultsItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = fetchDataAsynchronously(page, query)
            LoadResult.Page(
                data = responseData,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private suspend fun fetchDataAsynchronously(position: Int, query: String): List<ResultsItem> {
        return suspendCoroutine { continuation ->
            apiService.searchKarakters(position,query).enqueue(object :
                Callback<KarakterResponse> {
                override fun onResponse(
                    call: Call<KarakterResponse>,
                    response: Response<KarakterResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        val karakterList = responseData?.results.orEmpty()
                        continuation.resume(karakterList as List<ResultsItem>)
                    } else {
                        continuation.resumeWithException(Exception("API request failed"))
                    }
                }

                override fun onFailure(call: Call<KarakterResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}