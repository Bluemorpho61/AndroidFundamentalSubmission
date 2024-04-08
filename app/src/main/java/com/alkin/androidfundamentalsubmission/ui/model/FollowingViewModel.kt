package com.alkin.androidfundamentalsubmission.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkin.androidfundamentalsubmission.response.GithubUserData
import com.alkin.androidfundamentalsubmission.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _userFollowingData = MutableLiveData<List<GithubUserData>>()
    val userFollowingData: LiveData<List<GithubUserData>> = _userFollowingData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getUserFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getUserFollowing(username)
        client.enqueue(object : Callback<List<GithubUserData>> {
            override fun onResponse(
                call: Call<List<GithubUserData>>, response: Response<List<GithubUserData>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowingData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GithubUserData>>, t: Throwable) {
                _isLoading.value =false
                Log.e(TAG, t.message.toString())
            }
        })
    }

    companion object{
       private val TAG =FollowingViewModel::class.java.simpleName
    }
}