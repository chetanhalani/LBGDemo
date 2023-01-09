package com.lbgdemo.domain.usecase


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbgdemo.BuildConfig
import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.db.LBGDatabase
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.local.ArtistLocalDataSourceImpl
import com.lbgdemo.data.remote.ArtistRemoteDataSourceImpl
import com.lbgdemo.data.respository.ArtistRepositoryImpl
import com.lbgdemo.presentation.ArtistViewModel
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

    private lateinit var viewModel: ArtistViewModel

    private lateinit var db: LBGDatabase

    @Mock
    lateinit var lbgService: LBGDemoService

    val fields = BuildConfig.QUERY_FIELDS

    val fakeArtistData = listOf(
        Artist(1,"Chetan","Intermediate","xyz_abc"),
        Artist(2,"Mohan","Expert","abc_xyz")
    )
    val fakeArtistListData = ArtistList(fakeArtistData)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LBGDatabase::class.java)
            .build()
        val artistLocalDataSource = ArtistLocalDataSourceImpl(db.artistDao())
        val artistRemoteDatasource = ArtistRemoteDataSourceImpl(lbgService, fields)

        val artistRepository = ArtistRepositoryImpl(artistRemoteDatasource, artistLocalDataSource)
        viewModel = ArtistViewModel(GetArtistsUseCase(artistRepository))
    }

    @Test
    fun `test results found from remote are saved and returned`() {
        runBlocking {
            Mockito.`when`(lbgService.getArtists(fields)).thenReturn(Response.success(fakeArtistListData))
            val resultData = viewModel.getArtists().awaitForValue()
            Assert.assertEquals(resultData, fakeArtistData)
        }
    }

    @After
    fun closeDb() {
        db.close()
    }

}
