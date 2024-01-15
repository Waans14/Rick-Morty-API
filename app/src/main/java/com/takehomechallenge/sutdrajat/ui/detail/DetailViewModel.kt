package com.takehomechallenge.sutdrajat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter
import com.takehomechallenge.sutdrajat.data.repository.KarakterRepository

class DetailViewModel (private val repository: KarakterRepository) : ViewModel() {

    fun getFavoriteKarakterByName(name: String): LiveData<FavoriteKarakter> {
        val favoriteKarakterLiveData = MutableLiveData<FavoriteKarakter>()
        repository.getFavoriteKarakterByName(name).observeForever { favoriteKarakter ->
            favoriteKarakterLiveData.value = favoriteKarakter
        }

        return favoriteKarakterLiveData
    }

    fun insertFavoriteKarakter(favoriteKarakter: FavoriteKarakter) {
        repository.insertFavoriteKarakter(favoriteKarakter)
    }

    fun deleteFavoriteKarakterByName(name: String) {
        repository.deleteFavoriteKarakterByName(name)
    }
}