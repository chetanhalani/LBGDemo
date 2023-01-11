package com.lbgdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import com.lbgdemo.data.respository.ArtistRepository
import com.lbgdemo.data.respository.ArtistRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException

class ArtistRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var artistRemoteDatasource: ArtistRemoteDatasource

    @Mock
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
        MockitoAnnotations.initMocks(this)
        artistRepository = ArtistRepositoryImpl(artistRemoteDatasource, artistLocalDatasource)
    }

    @Test
    fun `test getArtists list returned from server`() {
        runBlocking {
            Mockito.`when`(artistRemoteDatasource.getArtists())
                .thenReturn(fakeArtistListDataResponse)
            Mockito.`when`(artistLocalDatasource.getArtists()).thenReturn(emptyList())
            val resultData = artistRepository.getArtists()
            assertEquals(resultData, fakeArtistListDataResponse)
        }
    }

    @Test
    fun `test results list not returned from server`() {
        runBlocking {
            Mockito.`when`(artistRemoteDatasource.getArtists())
                .thenReturn(fakeErrorArtistListDataResponse)
            Mockito.`when`(artistLocalDatasource.getArtists()).thenReturn(emptyList())
            val resultData = artistRepository.getArtists()
            assertEquals(resultData, fakeErrorArtistListDataResponse)
        }
    }

    @Test
    fun `test results list not returned from server but local returned list`() {
        runBlocking {
            Mockito.`when`(artistRemoteDatasource.getArtists())
                .thenReturn(fakeErrorArtistListDataResponse)
            Mockito.`when`(artistLocalDatasource.getArtists()).thenReturn(fakeArtistData)
            val resultData = artistRepository.getArtists()
            assertEquals(
                (resultData as DataResponse.Success<ArtistList>).data.artists,
                fakeArtistData
            )
        }
    }

    @Test
    fun `test results list not returned from local thrown exception on server`() {
        runBlocking {
            val mockitoException = MockitoException("Unknown Exception")
            Mockito.`when`(artistRemoteDatasource.getArtists()).thenThrow(mockitoException)
            Mockito.`when`(artistLocalDatasource.getArtists()).thenReturn(fakeArtistData)
            val resultData = artistRepository.getArtists()
            assertEquals(
                (resultData as DataResponse.Success<ArtistList>).data.artists,
                fakeArtistData
            )
        }
    }
}