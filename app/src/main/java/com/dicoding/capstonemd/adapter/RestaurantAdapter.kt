package com.dicoding.capstonemd.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.capstonemd.BuildConfig
import com.dicoding.capstonemd.data.api.Restaurant
import com.dicoding.capstonemd.data.remote.response.RestaurantsItem
import com.dicoding.capstonemd.databinding.RestaurantRecommendationRvBinding
import com.dicoding.capstonemd.ui.maps.MapsActivity
import com.google.gson.Gson

class RestaurantAdapter : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = RestaurantRecommendationRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MapsActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class RestaurantViewHolder(private val binding: RestaurantRecommendationRvBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(restaurant: Restaurant) {
            binding.simpleTitleText.text = restaurant.name
            binding.ratingTextView.text = restaurant.rating.toString() + " "
            binding.simpleSubtitleText.text = restaurant.vicinity

            val apiKey = BuildConfig.API_KEY
            val imageUrl = getPhotoUrl(restaurant.photoReference, apiKey)
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(binding.IvAvatar)
        }
    }

    fun getPhotoUrl(photoReference: String, apiKey: String, maxWidth: Int = 400): Uri {
        val url =
            "https://maps.googleapis.com/maps/api/place/photo?maxwidth=$maxWidth&photoreference=$photoReference&key=$apiKey"
        return Uri.parse(url)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Restaurant>() {
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }
    }

}