package com.takehomechallenge.sutdrajat.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter
import com.takehomechallenge.sutdrajat.data.local.room.KarakterDao
import com.takehomechallenge.sutdrajat.data.remote.response.ResultsItem
import com.takehomechallenge.sutdrajat.data.remote.retrofit.ApiService
import com.takehomechallenge.sutdrajat.data.repository.source.KarakterPagingSource
import com.takehomechallenge.sutdrajat.data.repository.source.KarakterSearchPagingSource
import com.takehomechallenge.sutdrajat.utils.AppExecutors

class KarakterRepository private constructor(
    private val apiService: ApiService,
    private val favoriteKarakterDao: KarakterDao,
    private val appExecutors: AppExecutors
){

    fun getKarakter(): LiveData<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                KarakterPagingSource(apiService)
            }
        ).liveData
    }

    fun searchKarakter(query: String): LiveData<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                KarakterSearchPagingSource(apiService, query)
            }
        ).liveData
    }

    fun getAllFavoriteKarakter(): LiveData<List<FavoriteKarakter>> {
        return favoriteKarakterDao.getAllKarakter()
    }

    fun insertFavoriteKarakter(favoriteUser: FavoriteKarakter) {
        appExecutors.diskIO.execute {
            favoriteKarakterDao.insert(favoriteUser)
        }
    }

    fun getFavoriteKarakterByName(name: String): LiveData<FavoriteKarakter> {
        return favoriteKarakterDao.getFavoriteKarakterByName(name)
    }

    fun deleteFavoriteKarakterByName(name: String) {
        appExecutors.diskIO.execute {
            favoriteKarakterDao.deleteFavoriteKarakterByName(name)
        }
    }

    companion object {
        @Volatile
        private var instance: KarakterRepository? = null

        fun getInstance(
            apiService: ApiService,
            favoriteKarakterDao: KarakterDao,
            appExecutors: AppExecutors
        ): KarakterRepository =
            instance ?: synchronized(this) {
                instance ?: KarakterRepository(apiService, favoriteKarakterDao, appExecutors)
            }.also { instance = it }
    }
}