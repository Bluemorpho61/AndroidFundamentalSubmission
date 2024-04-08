package com.alkin.androidfundamentalsubmission.ui.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkin.androidfundamentalsubmission.adapter.UserAdapter
import com.alkin.androidfundamentalsubmission.databinding.ActivityMainBinding
import com.alkin.androidfundamentalsubmission.ui.model.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }
        with(binding) {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                queryHint = "Cari username github disini"
                this.setOnQueryTextListener(object : OnQueryTextListener,
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        mainViewModel.findUsers(query)
                        return false
                    }
                })
            }
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.githubUserData.observe(this) {
            binding.rvHasil.layoutManager = LinearLayoutManager(this)
            val adapter = UserAdapter()
            adapter.submitList(it)
            binding.rvHasil.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvHasil.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvHasil.visibility = View.VISIBLE
        }
    }

}