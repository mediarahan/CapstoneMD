package com.dicoding.capstonemd.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.capstonemd.data.Fake
import com.dicoding.capstonemd.databinding.SimpleRecommendationRvBinding
import com.dicoding.capstonemd.ui.detail.DetailActivity

class FakeAdapter : ListAdapter<Fake, FakeAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SimpleRecommendationRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)

        holder.itemView.setOnClickListener {
            val name = items.name
            val avatar = items.avatarUrl
            val desc = items.desc
            val id = items.id
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("name",name)
            intentDetail.putExtra("avatar",avatar)
            intentDetail.putExtra("description", desc)
            intentDetail.putExtra("id",id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class ViewHolder(private val binding: SimpleRecommendationRvBinding):RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.IvAvatar
        fun bind(items: Fake) {
            binding.simpleTitleText.text = "${items.name}\n"
            binding.simpleSubtitleText.text = "${items.desc}\n"
            Glide.with(itemView)
                .load(items.avatarUrl)
                .into(imageView)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Fake>() {
            override fun areItemsTheSame(oldItem: Fake, newItem: Fake): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Fake, newItem: Fake): Boolean {
                return oldItem == newItem
            }
        }
    }
}