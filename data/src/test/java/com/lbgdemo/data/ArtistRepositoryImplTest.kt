package com.lbgdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import com.lbgdemo.domain.repository.ArtistRepository
import com.lbgdemo.data.respository.ArtistRepositoryImpl
import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse
import io.mockk.MockKAnnotations
import io.mockk.MockKException
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtistRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var artistRemoteDatasource: ArtistRemoteDatasource

    @MockK
    lateinit var artistLocalDatasource: ArtistLocalDataSource

    private lateinit var artistRepository: ArtistRepository

    private val fakeArtistData = listOf(
        Artist(1, "Chetan", "Intermediate", "xyz_abc"),
        Artist(2, "Mohan", "Expert", "abc_xyz")
    )
    private val fakeArtistListData = ArtistList(fakeArtistData)
    private val fakeArtistListDataResponse = DataResponse.Success(fakeArtistListData)
    private val fakeErrorArtistListDataResponse = DataResponse.Error("error message")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        artistRepository = ArtistRepositoryImpl(artistRemoteDatasource, artistLocalDatasource)
    }

    @Test
    fun `test getArtists list returned from server`() {
        runBlocking {
            coEvery { artistRemoteDatasource.getArtists() } returns fakeArtistListDataResponse
            coEvery { artistLocalDatasource.getArtists() } returns emptyList()
            val resultData = artistRepository.getArtists()
            assertTrue(resultData is DataResponse.Success<ArtistListEntity>)
            assertEquals((resultData as DataResponse.Success<ArtistListEntity>).data.artistList.size, fakeArtistListDataResponse.data.artists.size)
        }
    }

    @Test
    fun `test results list not returned from server`() {
        runBlocking {
            coEvery { artistRemoteDatasource.getArtists() } returns fakeErrorArtistListDataResponse
            coEvery { artistLocalDatasource.getArtists() } returns emptyList()
            val resultData = artistRepository.getArtists()
            assertEquals((resultData as DataResponse.Error).msg, fakeErrorArtistListDataResponse.msg)
        }
    }

    @Test
    fun `test results list not returned from server but local returned list`() {
        runBlocking {
            coEvery { artistRemoteDatasource.getArtists() } returns fakeErrorArtistListDataResponse
            coEvery { artistLocalDatasource.getArtists() } returns fakeArtistData
            val resultData = artistRepository.getArtists()
            assertEquals(
                (resultData as DataResponse.Success<ArtistListEntity>).data.artistList.size,
                fakeArtistData.size
            )
        }
    }

    @Test
    fun `test results list not returned from local thrown exception on server`() {
        runBlocking {
            val mockException = MockKException("Unknown Exception")
            coEvery { artistRemoteDatasource.getArtists() } throws mockException
            coEvery { artistLocalDatasource.getArtists() } returns fakeArtistData
            val resultData = artistRepository.getArtists()
            assertEquals(
                (resultData as DataResponse.Success<ArtistListEntity>).data.artistList.size,
                fakeArtistData.size
            )
        }
    }
}