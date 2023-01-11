package com.lbgdemo.domain


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.respository.ArtistRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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

    private lateinit var getArtistsUseCase: GetArtistsUseCase

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
