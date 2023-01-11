@file:Suppress("KDocUnresolvedReference")

package com.lbgdemo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lbgdemo.data.model.Artist
import com.lbgdemo.databinding.ListItemBinding
import com.lbgdemo.extension.setThumbnailImage

/**
 * RecyclerViewAdapter for Artist
 */
class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.MyViewHolder>() {
    private val artistList = ArrayList<Artist>()

    /**
     * Method to set data for the adapter
     */
    fun setList(artists: List<Artist>) {
        artistList.clear()
        artistList.addAll(artists)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(artistList[position])
    }

    /**
     * RecyclerViewViewHolder to show Artist
     * @property viewbinding of item layout
     */
    inner class MyViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Method to bind the data
         */
        fun bind(artist: Artist) {
            binding.titleTextView.text = artist.title ?: ""
            binding.descriptionTextView.text = artist.artistDisplay ?: ""
            binding.imageView.setThumbnailImage(artist.imageId)

        }
    }
}