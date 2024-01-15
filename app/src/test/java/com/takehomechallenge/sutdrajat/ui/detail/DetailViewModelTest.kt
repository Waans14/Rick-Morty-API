package com.takehomechallenge.sutdrajat.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter
import com.takehomechallenge.sutdrajat.data.repository.KarakterRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class DetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: KarakterRepository

    @Mock
    private lateinit var favoriteKarakterObserver: Observer<FavoriteKarakter>

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        detailViewModel = DetailViewModel(repository)
    }

    @Test
    fun `getFavoriteKarakterByName should return data from repository`() {
        val testName = "TestName"
        val testFavoriteKarakter = FavoriteKarakter(1,"image", testName, "species", "gender", "status", "origin", "location")

        `when`(repository.getFavoriteKarakterByName(testName)).thenReturn(
            MutableLiveData<FavoriteKarakter>().apply { value = testFavoriteKarakter }
        )

        detailViewModel.getFavoriteKarakterByName(testName).observeForever(favoriteKarakterObserver)
        verify(repository).getFavoriteKarakterByName(testName)
        verify(favoriteKarakterObserver).onChanged(testFavoriteKarakter)
    }

    @Test
    fun `test insertFavoriteKarakter`() {
        val fakeFavoriteKarakter = FavoriteKarakter(1)

        detailViewModel.insertFavoriteKarakter(fakeFavoriteKarakter)
        verify(repository).insertFavoriteKarakter(fakeFavoriteKarakter)
    }

    @Test
    fun `test deleteFavoriteKarakterByName`() {
        val fakeName = "John Doe"

        detailViewModel.deleteFavoriteKarakterByName(fakeName)
        verify(repository).deleteFavoriteKarakterByName(fakeName)
    }
}
