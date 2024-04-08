package com.alkin.androidfundamentalsubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkin.androidfundamentalsubmission.databinding.LayoutRvHasilBinding
import com.alkin.androidfundamentalsubmission.response.GithubUserData
import com.alkin.androidfundamentalsubmission.ui.view.DetailUserActivity
import com.bumptech.glide.Glide

class UserAdapter : ListAdapter<GithubUserData, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: LayoutRvHasilBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUserData) {
            binding.tvNamaUser.text = user.login
            Glide.with(itemView.context).load(user.avatarUrl).into(binding.imAvatar)
            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailUserActivity::class.java).apply {
                        putExtra(DetailUserActivity.USERNAMEFORDETAIL, user.login)
                        context.startActivity(this)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LayoutRvHasilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUserData>() {
            override fun areItemsTheSame(
                oldItem: GithubUserData,
                newItem: GithubUserData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubUserData,
                newItem: GithubUserData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}