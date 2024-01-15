package com.takehomechallenge.sutdrajat.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehomechallenge.sutdrajat.databinding.ActivityFavoriteBinding
import com.takehomechallenge.sutdrajat.ui.ViewModelFactory

@Suppress("DEPRECATION")
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@FavoriteActivity)

        with(binding){
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                title = "Favorite Karakter"
            }
        }

        setupFavoriteKarakter()
    }

    private fun setupFavoriteKarakter() {
        showLoading(true)
        val favoriteAdapter = FavoriteAdapter()
        binding.rvKarakter.layoutManager = LinearLayoutManager(this)
        viewModel.getAllFavoriteKarakter().observe(this) { favoriteKarakterList ->
            favoriteAdapter.submitList(favoriteKarakterList)
            binding.rvKarakter.adapter = favoriteAdapter
            showLoading(false)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}