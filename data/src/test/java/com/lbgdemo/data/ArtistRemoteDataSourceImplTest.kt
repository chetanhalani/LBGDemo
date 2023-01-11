package com.lbgdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.remote.ArtistRemoteDataSourceImpl
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException

class ArtistRemoteDataSourceImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var lbgService: LBGDemoService

    private val fields = BuildConfig.QUERY_FIELDS

    private val fakeArtistData = listOf(
        Artist(1, "Chetan", "Intermediate", "xyz_abc"),
        Artist(2, "Mohan", "Expert", "abc_xyz")
    )
    private val fakeArtistListData = ArtistList(fakeArtistData)

    private lateinit var artistRemoteDatasource: ArtistRemoteDatasource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        artistRemoteDatasource = ArtistRemoteDataSourceImpl(lbgService, fields)
    }

    @Test
    fun `test results list returned from server`() {
        runBlocking {
            Mockito.`when`(lbgService.getArtists(fields))
                .thenReturn(retrofit2.Response.success(fakeArtistListData))
            val resultData = artistRemoteDatasource.getArtists()
            assertTrue(resultData is DataResponse.Success<ArtistList>)
            assertEquals((resultData as DataResponse.Success<ArtistList>).data, fakeArtistListData)
        }
    }

    @Test
    fun `test results list not returned from server`() {
        runBlocking {
            Mockito.`when`(lbgService.getArtists(fields))
                .thenReturn(retrofit2.Response.error(500, "".toResponseBody()))
            val resultData = artistRemoteDatasource.getArtists()
            assertTrue(resultData is DataResponse.Error)
        }
    }

    @Test
    fun `test results list exception thrown from server`() {
        runBlocking {
            val mockitoException = MockitoException("Unknown Exception")
            Mockito.`when`(lbgService.getArtists(fields)).thenThrow(mockitoException)
            val resultData = artistRemoteDatasource.getArtists()
            assertTrue(resultData is DataResponse.Error)
        }
    }
}