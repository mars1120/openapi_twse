package com.stock.twse.homepage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stock.twse.data.BwibbuAll
import com.stock.twse.data.BwibbuInfo
import com.stock.twse.network.ITravelRepository
import com.stock.twse.network.TwseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@ExperimentalCoroutinesApi
class HomepageViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var twseRepository: ITravelRepository

    private lateinit var viewModel: HomepageViewModel
    private val gson = Gson()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomepageViewModel(twseRepository)
    }

    @Test
    fun verifyTwseApiResult() = runTest {

        // Prepare mock data
        val BwibbuAllResultData = getStringFromFiles("BwibbuAllResultData.txt")
        val type = object : TypeToken<List<BwibbuInfo>>() {}.type
        val mockBwibbuAll = gson.fromJson<List<BwibbuInfo>>(BwibbuAllResultData, type)


        // Mock repository behavior
        whenever(twseRepository.getBwibbuAll()).thenReturn(
            flowOf(Result.success(mockBwibbuAll))
        )

        // Call the method under test
        viewModel.fetchData()

        // Advance the dispatcher to run all coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify results
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)


        val actualBwibbuAllJson = gson.toJson(uiState.bwibbuAll)
        val expectedBwibbuAllJson = gson.toJson(mockBwibbuAll)
        assertEquals(actualBwibbuAllJson, expectedBwibbuAllJson)
    }

    @Ignore("Skip real API test")
    @Test
    fun verifyTwseApiWithRealRepository() {
        var realTwseRepository = TwseRepositoryImpl()
        viewModel = HomepageViewModel(realTwseRepository)

        viewModel.fetchData()
        testDispatcher.scheduler.advanceUntilIdle()

        Thread.sleep(5000)
        testDispatcher.scheduler.advanceUntilIdle()
        val uiState = viewModel.uiState.value

        assertFalse(uiState.isLoading)
        assertNull(uiState.error)

        assertNotNull(uiState.bwibbuAll)
        assertTrue(uiState.bwibbuAll!!.isNotEmpty())

        uiState.bwibbuAll?.firstOrNull()?.let { firstItem ->
            assertNotNull(firstItem.Code)
            assertNotNull(firstItem.Name)

        }
    }

    private fun getStringFromFiles(fileName: String): String {
        val currentPath: Path = FileSystems.getDefault().getPath("").toAbsolutePath()
        var currentAbsPath =
            Paths.get(currentPath.toString() + "/src/test/java/com/stock/twse/resources/${fileName}")
        val content: String = String(Files.readAllBytes(currentAbsPath));
        return content
    }
}