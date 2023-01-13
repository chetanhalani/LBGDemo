package com.lbgdemo.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lbgdemo.R
import com.lbgdemo.databinding.FragmentArtistBinding
import com.lbgdemo.domain.model.DataResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistFragment : Fragment() {
    private lateinit var binding: FragmentArtistBinding

    private val artistViewModel: ArtistViewModel by viewModels()
    private lateinit var adapter: ArtistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    /**
     * Method to get artist and set into recycler view
     */
    private fun initRecyclerView() {
        binding.artistRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ArtistAdapter()
        binding.artistRecyclerView.adapter = adapter
        fetchArtists()
    }

    /**
     * Method to get artist
     */
    private fun fetchArtists() {
        binding.artistProgressBar.visibility = View.VISIBLE
        artistViewModel.artistList.observe(this) {
            when (it) {
                is DataResponse.Success -> {
                    adapter.setList(it.data.artistList)
                    adapter.notifyDataSetChanged()
                    if (it.data.artistList.isEmpty()) {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.no_records_found),
                            Snackbar.LENGTH_SHORT
                        ).show()
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