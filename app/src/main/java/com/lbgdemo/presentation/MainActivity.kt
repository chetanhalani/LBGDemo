package com.lbgdemo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.lbgdemo.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val artistViewModel: ArtistViewModel by viewModels()
    private lateinit var adapter: ArtistAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        initRecyclerView()
        setContentView(binding.root)
    }

    /**
     * Method to get artist and set into recycler view
     */
    private fun initRecyclerView() {
        binding.artistRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArtistAdapter()
        binding.artistRecyclerView.adapter = adapter
        fetchArtists()
    }

    /**
     * Method to get artist
     */
    private fun fetchArtists() {
        binding.artistProgressBar.visibility = View.VISIBLE
        val responseLiveData = artistViewModel.getArtists()
        responseLiveData.observe(this) {
            when(it) {
                is DataResponse.Success -> {
                    adapter.setList(it.data.artists)
                    adapter.notifyDataSetChanged()
                    if(it.data.artists.isEmpty()) {
                        Snackbar.make(binding.root, getString(R.string.no_records_found), Snackbar.LENGTH_SHORT).show()
                    }
                }
                is DataResponse.Error -> {
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
                }
            }
            binding.artistProgressBar.visibility = View.GONE
        }
    }

}