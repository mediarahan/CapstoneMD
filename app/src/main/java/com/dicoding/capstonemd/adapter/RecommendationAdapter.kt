package com.dicoding.capstonemd.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.capstonemd.data.local.fake.Fake
import com.dicoding.capstonemd.data.ml.Recommendation
import com.dicoding.capstonemd.databinding.SimpleRecommendationRvBinding
import com.dicoding.capstonemd.ui.detail.DetailActivity

class RecommendationAdapter : ListAdapter<Recommendation, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = SimpleRecommendationRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendation = getItem(position)
        holder.bind(recommendation)

        holder.itemView.setOnClickListener {
            val name = recommendation.name
            val avatar = recommendation.images?.firstOrNull()
            val desc = recommendation.mealTime
            val id = recommendation.id
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("name",name)
            intentDetail.putExtra("avatar",avatar)
            intentDetail.putExtra("description", desc)
            intentDetail.putExtra("id",id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class RecommendationViewHolder(private val binding: SimpleRecommendationRvBinding):RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.IvAvatar
        fun bind(recommendation: Recommendation) {
            binding.simpleTitleText.text = recommendation.name
            binding.simpleSubtitleText.text = recommendation.mealTime

            if (recommendation.images != null) {
                Glide.with(itemView)
                    .load(recommendation.images)
                    .into(imageView)
            } else {

            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recommendation>() {
            override fun areItemsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                return oldItem == newItem
            }
        }
    }
}