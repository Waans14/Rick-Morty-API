package com.takehomechallenge.sutdrajat.ui.favorite

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter
import com.takehomechallenge.sutdrajat.databinding.ListItemCharacterBinding
import com.takehomechallenge.sutdrajat.ui.detail.DetailActivity

class FavoriteAdapter : ListAdapter<FavoriteKarakter, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(private val binding: ListItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(karakter: FavoriteKarakter){
            binding.tvNama.text = karakter.name
            Glide.with(itemView)
                .load(karakter.image)
                .into(binding.ivImage)

            binding.cardView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("karakter_favorite", karakter)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivImage, "image"),
                        Pair(binding.tvNama, "judul"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteKarakter>() {
            override fun areItemsTheSame(oldItem: FavoriteKarakter, newItem: FavoriteKarakter): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteKarakter, newItem: FavoriteKarakter): Boolean {
                return oldItem == newItem
            }
        }
    }
}