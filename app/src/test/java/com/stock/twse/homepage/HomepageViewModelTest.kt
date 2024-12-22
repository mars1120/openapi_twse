package com.stock.twse.homepage

import StockDayAvgAll
import StockDayAvgAllItem
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stock.twse.StockDayAll
import com.stock.twse.StockDayAllItem
import com.stock.twse.data.BwibbuInfo
import com.stock.twse.network.ITravelRepository
import com.stock.twse.network.TwseRepositoryImpl
import com.stock.twse.utils.ArrayUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
        val mockBwibbuAll = gson.fromJson<List<BwibbuInfo>>(
            getStringFromFiles("StockDayAllResultData.txt"),
            object : TypeToken<List<BwibbuInfo>>() {}.type
        )

        val mockStockDayAll = gson.fromJson<List<StockDayAllItem>>(
            getStringFromFiles("StockDayAllResultData.txt"),
            object : TypeToken<List<StockDayAllItem>>() {}.type
        )

        val mockStockDayAvgAll = gson.fromJson<List<StockDayAvgAllItem>>(
            getStringFromFiles("StockDayAvgAllResultData.txt"),
            object : TypeToken<List<StockDayAvgAllItem>>() {}.type
        )


        // Mock repository behavior
        whenever(twseRepository.getBwibbuAll()).thenReturn(
            flowOf(Result.success(mockBwibbuAll))
        )

        whenever(twseRepository.getStockDayAll()).thenReturn(
            flowOf(Result.success(mockStockDayAll))
        )

        whenever(twseRepository.getStockDayAvgAll()).thenReturn(
            flowOf(Result.success(mockStockDayAvgAll))
        )


        // Call the method under test
        viewModel.fetchData(true)

        // Advance the dispatcher to run all coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify results
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)


        val actualBwibbuAllJson = gson.toJson(uiState.bwibbuAll)
        val expectedBwibbuAllJson = gson.toJson(mockBwibbuAll)
        assertEquals(actualBwibbuAllJson, expectedBwibbuAllJson)
        assertEquals(gson.toJson(uiState.stockDayAll), gson.toJson(mockStockDayAll))
        assertEquals(gson.toJson(uiState.stockDayAvgAll), gson.toJson(mockStockDayAvgAll))
    }

    @Ignore("Skip real API test")
    @Test
    fun verifyTwseApiWithRealRepository() {
        var realTwseRepository = TwseRepositoryImpl()
        viewModel = HomepageViewModel(realTwseRepository)

        viewModel.fetchData(true)
        testDispatcher.scheduler.advanceUntilIdle()

        Thread.sleep(5000)
        testDispatcher.scheduler.advanceUntilIdle()
        Thread.sleep(5000)
        testDispatcher.scheduler.advanceUntilIdle()
        val uiState = viewModel.uiState.value

        assertFalse(uiState.isLoading)
        assertNull(uiState.error)

        assertNotNull(uiState.bwibbuAll)
        assertTrue(uiState.bwibbuAll!!.isNotEmpty())
        assertTrue(uiState.stockDayAll!!.isNotEmpty())
        assertTrue(uiState.stockDayAvgAll!!.isNotEmpty())
        uiState.bwibbuAll?.firstOrNull()?.let { firstItem ->
            assertNotNull(firstItem.Code)
            assertNotNull(firstItem.Name)
        }
    }


    @Test
    fun verifyMergeSortedArraysByHashMapFilter() {
        // Prepare mock data
        val mockStockDayAll = gson.fromJson<StockDayAll>(
            getStringFromFiles("StockDayAllResultData.txt"),
            object : TypeToken<StockDayAll>() {}.type
        )

        val mockStockDayAvgAll = gson.fromJson<StockDayAvgAll>(
            getStringFromFiles("StockDayAvgAllResultData.txt"),
            object : TypeToken<StockDayAvgAll>() {}.type
        )

        val mockStockDayAllSet = mockStockDayAll.map { it.Code }.toHashSet()

        val mergeStockDayAvgAll = mockStockDayAvgAll.filter { mockStockDayAllSet.contains(it.Code) }
            .map { it }

        val mergeStockDayAvgAllSet = mergeStockDayAvgAll.map { it.Code }.toHashSet()

        val missingCodes = mockStockDayAll!!.filter { !mergeStockDayAvgAllSet.contains(it.Code) }
            .map { it.Code }

        assertEquals(missingCodes[0], "00707R")
    }

    @Test
    fun verifyMergeSortedArrays() {
        // Prepare mock data
        val mockStockDayAll = gson.fromJson<StockDayAll>(
            getStringFromFiles("StockDayAllResultData.txt"),
            object : TypeToken<StockDayAll>() {}.type
        )

        val mockStockDayAvgAll = gson.fromJson<StockDayAvgAll>(
            getStringFromFiles("StockDayAvgAllResultData.txt"),
            object : TypeToken<StockDayAvgAll>() {}.type
        )

        var mergeStockDayAll = ArrayUtils.mergeSortedArrays(
            mockStockDayAll,
            mockStockDayAvgAll
        )

        val mergeStockDayAllSet = mergeStockDayAll!!.map { it.Code }.toHashSet()
        val missingCodes = mockStockDayAll!!.filter { !mergeStockDayAllSet.contains(it.Code) }
            .map { it.Code }

        assertEquals(missingCodes[0], "00707R")

    }


    private fun getStringFromFiles(fileName: String): String {
        val currentPath: Path = FileSystems.getDefault().getPath("").toAbsolutePath()
        var currentAbsPath =
            Paths.get(currentPath.toString() + "/src/test/java/com/stock/twse/resources/${fileName}")
        val content: String = String(Files.readAllBytes(currentAbsPath));
        return content
    }
}