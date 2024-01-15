package com.takehomechallenge.sutdrajat.data.di

import android.content.Context
import com.takehomechallenge.sutdrajat.data.local.room.KarakterRoomDatabase
import com.takehomechallenge.sutdrajat.data.remote.retrofit.ApiConfig
import com.takehomechallenge.sutdrajat.data.repository.KarakterRepository
import com.takehomechallenge.sutdrajat.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): KarakterRepository {
        val apiService = ApiConfig.getApiService()
        val database = KarakterRoomDatabase.getDatabase(context)
        val dao = database.favoriteKarakterDao()
        val appExecutors = AppExecutors()
        return KarakterRepository.getInstance(apiService, dao, appExecutors)
    }
}