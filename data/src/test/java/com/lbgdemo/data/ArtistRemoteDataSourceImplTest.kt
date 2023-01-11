package com.lbgdemo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.remote.ArtistRemoteDataSourceImpl
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import io.mockk.MockKAnnotations
import io.mockk.MockKException
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtistRemoteDataSourceImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
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
        MockKAnnotations.init(this, relaxUnitFun = true)
        artistRemoteDatasource = ArtistRemoteDataSourceImpl(lbgService, fields)
    }

    @Test
    fun `test results list returned from server`() {
        runBlocking {
            coEvery { lbgService.getArtists(fields) } returns retrofit2.Response.success(fakeArtistListData)
            val resultData = artistRemoteDatasource.getArtists()
            assertTrue(resultData is DataResponse.Success<ArtistList>)
            assertEquals((resultData as DataResponse.Success<ArtistList>).data, fakeArtistListData)
        }
    }

    @Test
    fun `test results list not returned from server`() {
        runBlocking {
            coEvery { lbgService.getArtists(fields) } returns retrofit2.Response.error(500, "".toResponseBody())
            val resultData = artistRemoteDatasource.getArtists()
            assertTrue(resultData is DataResponse.Error)
        }
    }

    @Test
    fun `test results list exception thrown from server`() {
        runBlocking {
            val mockException = MockKException("Unknown Exception")
            coEvery { lbgService.getArtists(fields) } throws mockException
            val resultData = artistRemoteDatasource.getArtists()
            assertTrue(resultData is DataResponse.Error)
        }
    }
}