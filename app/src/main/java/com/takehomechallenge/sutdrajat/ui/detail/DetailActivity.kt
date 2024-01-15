package com.takehomechallenge.sutdrajat.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.takehomechallenge.sutdrajat.R
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter
import com.takehomechallenge.sutdrajat.data.remote.response.ResultsItem
import com.takehomechallenge.sutdrajat.databinding.ActivityDetailBinding
import com.takehomechallenge.sutdrajat.ui.ViewModelFactory

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var favoriteKarakter: FavoriteKarakter? = null
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = obtainViewModel(this@DetailActivity)
        favoriteKarakter = FavoriteKarakter()

        with(binding){
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                title = "Detail Karakter"
            }
        }

        setupData()
    }

    private fun setupData() {
        val karakterFavoriteItem = intent.getParcelableExtra<FavoriteKarakter>("karakter_favorite")
        if (karakterFavoriteItem != null){
            Glide.with(applicationContext)
                .load(karakterFavoriteItem.image)
                .into(binding.ivImage)
            binding.tvNama.text = karakterFavoriteItem.name
            binding.tvSpecies.text = karakterFavoriteItem.species
            binding.tvGender.text = karakterFavoriteItem.gender
            binding.tvStatus.text = karakterFavoriteItem.status
            binding.tvOrigin.text = karakterFavoriteItem.origin
            binding.tvLocation.text = karakterFavoriteItem.location

            favoriteKarakter?.image = karakterFavoriteItem.image
            favoriteKarakter?.name = karakterFavoriteItem.name
            favoriteKarakter?.species = karakterFavoriteItem.species
            favoriteKarakter?.gender = karakterFavoriteItem.gender
            favoriteKarakter?.status = karakterFavoriteItem.status
            favoriteKarakter?.origin = karakterFavoriteItem.origin
            favoriteKarakter?.location = karakterFavoriteItem.location

            setFavoriteKarakter(karakterFavoriteItem.name)
        }else{
            val karakterItem = intent.getParcelableExtra<ResultsItem>("karakter") as ResultsItem
            Glide.with(applicationContext)
                .load(karakterItem.image)
                .into(binding.ivImage)
            binding.tvNama.text = karakterItem.name
            binding.tvSpecies.text = karakterItem.species
            binding.tvGender.text = karakterItem.gender
            binding.tvStatus.text = karakterItem.status
            binding.tvOrigin.text = karakterItem.origin?.name
            binding.tvLocation.text = karakterItem.location?.name

            favoriteKarakter?.image = karakterItem.image
            favoriteKarakter?.name = karakterItem.name
            favoriteKarakter?.species = karakterItem.species
            favoriteKarakter?.gender = karakterItem.gender
            favoriteKarakter?.status = karakterItem.status
            favoriteKarakter?.origin = karakterItem.origin?.name
            favoriteKarakter?.location = karakterItem.location?.name

            setFavoriteKarakter(karakterItem.name)
        }

    }

    private fun setFavoriteKarakter(name: String?) {
        name?.let {
            detailViewModel.getFavoriteKarakterByName(it).observe(this) { favoriteKarakterFromDb ->
                if (favoriteKarakterFromDb == null) {
                    binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                    binding.fab.setOnClickListener {
                        detailViewModel.insertFavoriteKarakter(favoriteKarakter as FavoriteKarakter)
                        binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                    }
                } else {
                    binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                    binding.fab.setOnClickListener {
                        detailViewModel.deleteFavoriteKarakterByName(name)
                        binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }
}