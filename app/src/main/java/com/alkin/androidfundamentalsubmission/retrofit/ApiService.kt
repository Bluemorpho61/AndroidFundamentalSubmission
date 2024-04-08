package com.alkin.androidfundamentalsubmission.retrofit

import com.alkin.androidfundamentalsubmission.response.DetailUserResponse
import com.alkin.androidfundamentalsubmission.response.GithubResponse
import com.alkin.androidfundamentalsubmission.response.GithubUserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsername(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("https://api.github.com/users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<GithubUserData>>

    @GET("https://api.github.com/users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<GithubUserData>>

    @GET("users")
    fun getUserHome(): Call<List<GithubUserData>>
}