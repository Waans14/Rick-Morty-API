package com.takehomechallenge.sutdrajat.ui.main

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.takehomechallenge.sutdrajat.data.remote.response.ResultsItem
import com.takehomechallenge.sutdrajat.databinding.ListItemCharacterBinding
import com.takehomechallenge.sutdrajat.ui.detail.DetailActivity

class KarakterAdapter : PagingDataAdapter<ResultsItem, KarakterAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        if (review != null) {
            holder.bind(review)
        }
    }

    class MyViewHolder(private val binding: ListItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(karakter: ResultsItem){
            binding.tvNama.text = karakter.name
            Glide.with(itemView)
                .load(karakter.image)
                .into(binding.ivImage)

            binding.cardView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("karakter", karakter)

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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}