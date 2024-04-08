package com.alkin.androidfundamentalsubmission.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkin.androidfundamentalsubmission.adapter.SectionPageAdapter
import com.alkin.androidfundamentalsubmission.adapter.UserAdapter
import com.alkin.androidfundamentalsubmission.databinding.FragmentFollowBinding
import com.alkin.androidfundamentalsubmission.response.GithubUserData
import com.alkin.androidfundamentalsubmission.ui.model.FollowerViewModel
import com.alkin.androidfundamentalsubmission.ui.model.FollowingViewModel
import com.alkin.androidfundamentalsubmission.ui.view.DetailUserActivity


class FollowFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentFollowBinding
    private val followerViewModel: FollowerViewModel by viewModels()
    private val followingViewModel: FollowingViewModel by viewModels()
    private lateinit var followType: String
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentFollowBinding.inflate(inflater, container, false)
        username = arguments?.getString(SectionPageAdapter.USERNAME).toString()
        followType = arguments?.getString(SectionPageAdapter.SECTION_FOLLOWTYPE).toString()

        if (followType == SectionPageAdapter.SECTION_FOLLOWERS) {
            if (followerViewModel.userFollowerData.value.isNullOrEmpty()) {
                followerViewModel.getUserFollowers(username)
            }

            followerViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            followerViewModel.userFollowerData.observe(viewLifecycleOwner) {
                setFollow(it)
            }
            followerViewModel.userFollowerData.observe(viewLifecycleOwner) {
                val adapter = UserAdapter()
                adapter.submitList(it)
                fragmentBinding.rvFollow.adapter = adapter

            }

        } else {
            if (followingViewModel.userFollowingData.value.isNullOrEmpty()) {
                followingViewModel.getUserFollowing(username)
            }
            followingViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            followingViewModel.userFollowingData.observe(viewLifecycleOwner) {
                setFollow(it)
            }
            followingViewModel.userFollowingData.observe(viewLifecycleOwner) {
                val adapter = UserAdapter()
                adapter.submitList(it)
                fragmentBinding.rvFollow.adapter = adapter
            }
        }

        return fragmentBinding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentBinding.root.requestLayout()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            fragmentBinding.progressBarDetail.visibility = View.VISIBLE
        } else {
            fragmentBinding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun setFollow(users: List<GithubUserData>) {
        val layoutManager = LinearLayoutManager(context)
        fragmentBinding.rvFollow.layoutManager = layoutManager
        val adapter = UserAdapter()
        adapter.submitList(users)
        fragmentBinding.rvFollow.adapter = adapter
    }

    companion object {
        fun newInstance(username: String, followType: String): Fragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(SectionPageAdapter.USERNAME, username)
                    putString(SectionPageAdapter.SECTION_FOLLOWTYPE, followType)
                }
            }
        }
    }
}