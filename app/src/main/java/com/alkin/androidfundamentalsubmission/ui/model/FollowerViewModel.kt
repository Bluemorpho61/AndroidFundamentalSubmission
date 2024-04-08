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

class FollowerViewModel : ViewModel() {
    private val _userFollowerData = MutableLiveData<List<GithubUserData>>()
    val userFollowerData: LiveData<List<GithubUserData>> = _userFollowerData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getUserFollowers(username)
        client.enqueue(object : Callback<List<GithubUserData>> {
            override fun onResponse(
                call: Call<List<GithubUserData>>,
                response: Response<List<GithubUserData>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _userFollowerData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GithubUserData>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    companion object {
        private val TAG = FollowerViewModel::class.java.simpleName
    }
}