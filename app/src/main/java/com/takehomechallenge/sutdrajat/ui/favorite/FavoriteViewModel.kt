package com.takehomechallenge.sutdrajat.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter
import com.takehomechallenge.sutdrajat.data.repository.KarakterRepository

class FavoriteViewModel(private val repository: KarakterRepository) : ViewModel() {

    fun getAllFavoriteKarakter(): LiveData<List<FavoriteKarakter>> {
        return repository.getAllFavoriteKarakter()
    }
}
