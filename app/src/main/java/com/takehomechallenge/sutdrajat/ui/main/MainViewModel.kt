package com.takehomechallenge.sutdrajat.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.takehomechallenge.sutdrajat.data.remote.response.ResultsItem
import com.takehomechallenge.sutdrajat.data.repository.KarakterRepository

class MainViewModel(private val repository: KarakterRepository) : ViewModel() {

    fun getKarakter(): LiveData<PagingData<ResultsItem>> {
        return repository.getKarakter().cachedIn(viewModelScope)
    }

    fun searchKarakter(nama: String): LiveData<PagingData<ResultsItem>> {
        return repository.searchKarakter(nama).cachedIn(viewModelScope)
    }

}
