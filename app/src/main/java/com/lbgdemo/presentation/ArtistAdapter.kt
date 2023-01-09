package com.lbgdemo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lbgdemo.BuildConfig
import com.lbgdemo.data.model.Artist
import com.lbgdemo.R
import com.lbgdemo.databinding.ListItemBinding


class ArtistAdapter():RecyclerView.Adapter<MyViewHolder>() {
    private val artistList = ArrayList<Artist>()

    fun setList(artists:List<Artist>){
         artistList.clear()
         artistList.addAll(artists)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(artistList[position])
    }
}



class MyViewHolder(val binding: ListItemBinding):
RecyclerView.ViewHolder(binding.root){

   fun bind(artist:Artist){
        binding.titleTextView.text = artist.title ?: ""
        binding.descriptionTextView.text = artist.artistDisplay ?: ""
       artist.imageId?.let {
           val posterURL = BuildConfig.IMAGE_URL + "iiif/2/" + it + "/full/100,/0/default.jpg"
           Glide.with(binding.imageView.context)
               .load(posterURL)
               .into(binding.imageView)

       }

   }

}