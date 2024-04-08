package com.alkin.androidfundamentalsubmission.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkin.androidfundamentalsubmission.response.DetailUserResponse
import com.alkin.androidfundamentalsubmission.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _userDataDetail = MutableLiveData<DetailUserResponse>()
    val userDataDetail: LiveData<DetailUserResponse> = _userDataDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getDetailUser(username: String? = null) {
        if (username.isNullOrEmpty()) {
            Log.e(TAG, "Error: ${Log.ERROR}")
        } else {
            _isLoading.value = true
            val client = ApiConfig.getApiServices().getDetailUser(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _userDataDetail.value =response.body()
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, t.message.toString())
                }
            })
        }
    }

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }
}