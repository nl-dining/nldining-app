package com.nldining.app

import com.nldining.app.models.NominatimResponse
import com.nldining.app.network.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class NominatimServiceTest {

    private val mockService: ApiService = mockk()

    @Test
    fun `searchLocation returns expected coordinates`() = runTest {
        // Arrange
        val expected = listOf(NominatimResponse(lat = "52.3702", lon = "4.8952", display_name = "test"))
        coEvery { mockService.searchLocation("Amsterdam") } returns expected

        // Act
        val result = mockService.searchLocation("Amsterdam")

        // Assert
        assertEquals("52.3702", result.first().lat)
        assertEquals("4.8952", result.first().lon)
    }
}