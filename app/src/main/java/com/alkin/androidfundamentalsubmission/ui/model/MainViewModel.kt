package com.alkin.androidfundamentalsubmission.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkin.androidfundamentalsubmission.response.GithubResponse
import com.alkin.androidfundamentalsubmission.response.GithubUserData
import com.alkin.androidfundamentalsubmission.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _githubUserData = MutableLiveData<List<GithubUserData>>()
    val githubUserData: LiveData<List<GithubUserData>> = _githubUserData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getGithubUser()
    }

    private fun getGithubUser() {
        _isLoading.value = true
        _githubUserData.value = arrayListOf()
        val client = ApiConfig.getApiServices().getUserHome()
        client.enqueue(object : Callback<List<GithubUserData>> {
            override fun onResponse(
                call: Call<List<GithubUserData>>,
                response: Response<List<GithubUserData>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GithubUserData>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }
        })
    }

     fun findUsers(username: String? = null) {

        if (username.isNullOrEmpty()) {
            getGithubUser()
        } else {
            _isLoading.value = true
            _githubUserData.value = arrayListOf()
            val client = ApiConfig.getApiServices().searchUsername(username)
            client.enqueue(object : Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _githubUserData.value = response.body()?.items
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                    _isLoading.value = false
                }

            })

        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}