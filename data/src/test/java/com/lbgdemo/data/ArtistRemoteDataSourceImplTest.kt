package com.lbgdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.remote.ArtistRemoteDataSourceImpl
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Assert.*
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

    val fields = BuildConfig.QUERY_FIELDS

    val fakeArtistData = listOf(
        Artist(1,"Chetan","Intermediate","xyz_abc"),
        Artist(2,"Mohan","Expert","abc_xyz")
    )
    val fakeArtistListData = ArtistList(fakeArtistData)

    lateinit var artistRemoteDatasource : ArtistRemoteDatasource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        artistRemoteDatasource = ArtistRemoteDataSourceImpl(lbgService, fields)
    }

    @Test
    fun `test results list returned from server`() {
        runBlocking {
            Mockito.`when`(lbgService.getArtists(fields)).thenReturn(retrofit2.Response.success(fakeArtistListData))
            val resultData = artistRemoteDatasource.getArtists()
            Assert.assertTrue(resultData is DataResponse.Success<ArtistList>)
            Assert.assertEquals((resultData as DataResponse.Success<ArtistList>).data, fakeArtistListData)
        }
    }

    @Test
    fun `test results list not returned from server`() {
        runBlocking {
            Mockito.`when`(lbgService.getArtists(fields)).thenReturn(retrofit2.Response.error(500, "".toResponseBody()))
            val resultData = artistRemoteDatasource.getArtists()
            Assert.assertTrue(resultData is DataResponse.Error)
        }
    }

    @Test
    fun `test results list exception thrown from server`() {
        runBlocking {
            val mockitoException = MockitoException("Unknown Exception")
            Mockito.`when`(lbgService.getArtists(fields)).thenThrow(mockitoException)
            val resultData = artistRemoteDatasource.getArtists()
            Assert.assertTrue(resultData is DataResponse.Error)
        }
    }
}