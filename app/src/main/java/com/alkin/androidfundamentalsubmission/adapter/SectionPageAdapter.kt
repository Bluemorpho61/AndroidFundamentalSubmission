package com.alkin.androidfundamentalsubmission.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alkin.androidfundamentalsubmission.ui.fragments.FollowFragment

class SectionPageAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(
            username, when (position) {
                0 -> SECTION_FOLLOWING
                else -> SECTION_FOLLOWERS
            }
        )
    }

    companion object {
        const val SECTION_FOLLOWERS = "followers"
        const val SECTION_FOLLOWING = "following"
        const val SECTION_FOLLOWTYPE = "type"
        const val USERNAME = "username"
    }
}