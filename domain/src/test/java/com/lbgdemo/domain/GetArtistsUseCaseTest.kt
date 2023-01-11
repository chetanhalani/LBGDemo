package com.lbgdemo.domain


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.db.LBGDatabase
import com.lbgdemo.data.local.ArtistLocalDataSourceImpl
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.remote.ArtistRemoteDataSourceImpl
import com.lbgdemo.data.respository.ArtistRepository
import com.lbgdemo.data.respository.ArtistRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class GetArtistsUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var artistRepository: ArtistRepository

    private val fakeArtistData = listOf(
        Artist(1, "Chetan", "Intermediate", "xyz_abc"),
        Artist(2, "Mohan", "Expert", "abc_xyz")
    )
    private val fakeArtistListData = ArtistList(fakeArtistData)
    private val fakeArtistListDataResponse = DataResponse.Success(fakeArtistListData)

    private lateinit var getArtistsUseCase : GetArtistsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getArtistsUseCase = GetArtistsUseCase(artistRepository)
    }

    @Test
    fun `test results found from remote are saved and returned`() {
        runBlocking {
            Mockito.`when`(artistRepository.getArtists())
                .thenReturn(fakeArtistListDataResponse)
            val resultData = getArtistsUseCase.execute()
            Assert.assertEquals(resultData, fakeArtistListDataResponse)
        }
    }

}
