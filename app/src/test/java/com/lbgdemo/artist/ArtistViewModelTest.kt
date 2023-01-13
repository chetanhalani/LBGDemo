package com.lbgdemo.artist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbgdemo.MainCoroutineRule
import com.lbgdemo.domain.GetArtistsUseCase
import com.lbgdemo.domain.model.ArtistEntity
import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ArtistViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineDispatcherRule = MainCoroutineRule()

    @MockK
    private lateinit var useCase: GetArtistsUseCase
    private lateinit var artistViewModel: ArtistViewModel

    private val fakeArtistData = listOf(
        ArtistEntity(1, "Chetan", "Intermediate", "xyz_abc"),
        ArtistEntity(2, "Mohan", "Expert", "abc_xyz")
    )
    private val fakeArtistListData = ArtistListEntity(fakeArtistData)
    private val fakeDataResponse = DataResponse.Success(fakeArtistListData)
    private val fakeErrorDataResponse = DataResponse.Error("")

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        artistViewModel = ArtistViewModel(useCase)
    }

    @Test
    fun `fetch artists success returned`() = runBlockingTest {
        coEvery { useCase.execute() } returns fakeDataResponse
        artistViewModel.getArtists()
        assertEquals(artistViewModel.artistList.value, fakeDataResponse)
    }

    @Test
    fun `fetch artists failure returned`() = runBlockingTest {
        coEvery { useCase.execute() } returns fakeErrorDataResponse
        artistViewModel.getArtists()
        assertEquals(artistViewModel.artistList.value, fakeErrorDataResponse)
    }

}

