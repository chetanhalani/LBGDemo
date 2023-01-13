package com.lbgdemo.domain


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lbgdemo.domain.model.ArtistEntity
import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse
import com.lbgdemo.domain.repository.ArtistRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetArtistsUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var artistRepository: ArtistRepository

    private val fakeArtistData = listOf(
        ArtistEntity(1, "Chetan", "Intermediate", "xyz_abc"),
        ArtistEntity(2, "Mohan", "Expert", "abc_xyz")
    )
    private val fakeArtistListData = ArtistListEntity(fakeArtistData)
    private val fakeArtistListDataResponse = DataResponse.Success(fakeArtistListData)

    private lateinit var getArtistsUseCase: GetArtistsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getArtistsUseCase = GetArtistsUseCaseImpl(artistRepository)
    }

    @Test
    fun `test results found from remote are saved and returned`() {
        runBlocking {
            coEvery { artistRepository.getArtists() } returns fakeArtistListDataResponse
            val resultData = getArtistsUseCase.execute()
            Assert.assertEquals(resultData, fakeArtistListDataResponse)
        }
    }

}
