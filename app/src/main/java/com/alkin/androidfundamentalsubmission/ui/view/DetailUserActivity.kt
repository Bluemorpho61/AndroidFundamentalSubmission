package com.alkin.androidfundamentalsubmission.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkin.androidfundamentalsubmission.R
import com.alkin.androidfundamentalsubmission.adapter.SectionPageAdapter
import com.alkin.androidfundamentalsubmission.databinding.ActivityDetailUserBinding
import com.alkin.androidfundamentalsubmission.ui.model.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.ln
import kotlin.math.pow


class DetailUserActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailUserBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var usernameForDetail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(detailBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(detailBinding.root) { v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }
        usernameForDetail = intent.getStringExtra(USERNAMEFORDETAIL) as String

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (detailViewModel.userDataDetail.value == null) {
            detailViewModel.getDetailUser(usernameForDetail)
        }
        detailViewModel.userDataDetail.observe(this) { user ->
            Glide.with(this).load(user.avatarUrl).into(detailBinding.imUser)
            with(detailBinding) {
                tvUsername.text = user.login
                tvNameUser.text = user.name
                tvFollowersValue.text = numberFormatter(user.followers.toLong())
                tvFollowingValue.text = numberFormatter(user.following.toLong())

            }
        }


        detailBinding.viewPager.adapter =
            SectionPageAdapter(this, usernameForDetail)
        TabLayoutMediator(detailBinding.tabLayout, detailBinding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun numberFormatter(count: Long): String {
        if (count < 1000) return "$count"
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        val value = count / 1000.0.pow(exp.toDouble())
        val suffix = "KMGTPE"[exp - 1]
        val formattedValue = if (value % 1 == 0.0) {
            String.format("%.0f", value)
        } else {
            String.format("%.1f", value)
        }

        return "$formattedValue$suffix"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            detailBinding.progressBarDetail.visibility = View.VISIBLE
            detailBinding.imUser.visibility = View.GONE
            detailBinding.tvUsername.visibility = View.GONE
            detailBinding.tvNameUser.visibility = View.GONE
        } else {
            detailBinding.progressBarDetail.visibility = View.GONE
            detailBinding.imUser.visibility = View.VISIBLE
            detailBinding.tvUsername.visibility = View.VISIBLE
            detailBinding.tvNameUser.visibility = View.VISIBLE
        }
    }

    companion object {
        const val USERNAMEFORDETAIL = "DATA"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}