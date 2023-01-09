package com.lbgdemo.data.respository

import android.util.Log
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import java.lang.Exception

class ArtistRepositoryImpl(
    private val artistRemoteDatasource: ArtistRemoteDatasource,
    private val artistLocalDataSource: ArtistLocalDataSource
) : ArtistRepository
{

    private lateinit var artistList : List<Artist>

    override suspend fun getArtists(): List<Artist>? {
        if(!this::artistList.isInitialized || artistList.isEmpty())
            artistList = fetchArtists()
        return artistList
    }

    suspend fun getArtistsFromAPI(): List<Artist> {
        lateinit var artistList: List<Artist>
        try {
            val response = artistRemoteDatasource.getArtists()
            val body = response.body()
            if(body!=null){
                artistList = body.artists
            }
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }
        return artistList
    }

    suspend fun fetchArtists():List<Artist>{
        lateinit var artistList: List<Artist>
        try {
           artistList = artistLocalDataSource.getArtists()
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }
        if(this::artistList.isInitialized && artistList.size>0){
            return artistList
        }else{
            artistList=getArtistsFromAPI()
            artistLocalDataSource.saveArtists(artistList)
        }

        return artistList
    }







}