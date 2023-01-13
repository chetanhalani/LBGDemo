package com.lbgdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbgdemo.data.db.ArtistDao
import com.lbgdemo.data.db.LBGDatabase
import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.local.ArtistLocalDataSourceImpl
import com.lbgdemo.data.model.Artist
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistLocalDataSourceImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var artistDao: ArtistDao

    private val fields = BuildConfig.QUERY_FIELDS

    private val fakeArtistData = listOf(
        Artist(1, "Chetan", "Intermediate", "xyz_abc"),
        Artist(2, "Mohan", "Expert", "abc_xyz")
    )
    private lateinit var artistLocalDataSource: ArtistLocalDataSource
    private lateinit var database: LBGDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LBGDatabase::class.java
        ).allowMainThreadQueries().build()
        artistDao = database.artistDao()
        artistLocalDataSource = ArtistLocalDataSourceImpl(artistDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `test results list returned from local`() {
        runBlocking {
            artistLocalDataSource.saveArtists(fakeArtistData)
            val resultData = artistLocalDataSource.getArtists()
            assertEquals(resultData, fakeArtistData)
        }
    }


    @Test
    fun `test results list cleared and empty`() {
        runTest {
            artistLocalDataSource.saveArtists(fakeArtistData)
            artistLocalDataSource.clearArtists()
            val resultData = artistLocalDataSource.getArtists()
            assertEquals(resultData.size, 0)
        }
    }

    @Test
    fun `test results list empty initially`() {
        runBlocking {
            val resultData = artistLocalDataSource.getArtists()
            assertTrue(resultData.isEmpty())
        }
    }
}