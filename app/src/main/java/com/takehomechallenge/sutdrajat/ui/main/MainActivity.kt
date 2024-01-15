package com.takehomechallenge.sutdrajat.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehomechallenge.sutdrajat.R
import com.takehomechallenge.sutdrajat.databinding.ActivityMainBinding
import com.takehomechallenge.sutdrajat.ui.ViewModelFactory
import com.takehomechallenge.sutdrajat.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                title = "The Rick and Morty API"
            }

            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.fav -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text

                    val karakter = searchView.text.toString()

                    searchKarakter(karakter)

                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()

                    false
                }
        }
        setupListKarakter()
    }

    private fun searchKarakter(karakter: String) {
        showLoading(true)
        val karakterAdapter = KarakterAdapter()
        binding.rvKarakter.layoutManager = LinearLayoutManager(this)
        binding.rvKarakter.adapter = karakterAdapter
        viewModel.searchKarakter(karakter).observe(this) {
            showLoading(false)
            karakterAdapter.submitData(lifecycle, it)
        }

    }

    private fun setupListKarakter() {
        showLoading(true)
        val karakterAdapter = KarakterAdapter()
        binding.rvKarakter.layoutManager = LinearLayoutManager(this)
        binding.rvKarakter.adapter = karakterAdapter
        viewModel.getKarakter().observe(this) {
            showLoading(false)
            karakterAdapter.submitData(lifecycle, it)
        }
    }


    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}