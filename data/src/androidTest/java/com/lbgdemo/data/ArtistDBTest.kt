package com.lbgdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbgdemo.data.db.ArtistDao
import com.lbgdemo.data.db.LBGDatabase
import com.lbgdemo.data.model.Artist
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistDBTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: ArtistDao
    private lateinit var database: LBGDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LBGDatabase::class.java
        ).build()
        dao = database.artistDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveArtistsTest() = runBlocking {
        val fakeArtists = listOf(
            Artist(1, "Chetan", "Intermediate", "xyz_abc"),
            Artist(2, "Mohan", "Expert", "abc_xyz")
        )
        dao.saveArtists(fakeArtists)

        val currentArtists = dao.getArtists()
        Assert.assertEquals(fakeArtists, currentArtists)
    }


    @Test
    fun deleteArtistTest() = runBlocking {
        val fakeArtists = listOf(
            Artist(1, "Chetan", "Intermediate", "xyz_abc"),
            Artist(2, "Mohan", "Expert", "abc_xyz")
        )
        dao.saveArtists(fakeArtists)
        dao.deleteAllArtists()
        val artistResult = dao.getArtists()
        Assert.assertTrue(artistResult.isEmpty())
    }
}









