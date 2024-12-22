package com.stock.twse.homepage

import StockDayAvgAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stock.twse.StockDayAll
import com.stock.twse.data.BwibbuAll
import com.stock.twse.network.ITravelRepository
import com.stock.twse.network.TwseRepositoryImpl
import com.stock.twse.utils.ArrayUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip

import kotlinx.coroutines.launch

data class HomepageUiState(
    val isLoading: Boolean = true,
    val stockDayAll: StockDayAll? = null,
    val stockDayAvgAll: StockDayAvgAll? = null,
    val bwibbuAll: BwibbuAll? = null,
    val error: String? = null,
)

class HomepageViewModel(
    private val repository: ITravelRepository = TwseRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomepageUiState())
    val uiState: StateFlow<HomepageUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null
    fun fetchData(skipFilter: Boolean = false) {
        fetchJob?.cancelChildren()

        fetchJob = viewModelScope.launch {
            repository.getBwibbuAll().cancellable().collect({ result ->

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        bwibbuAll = result.getOrNull(),
                        error = when {
                            result.isFailure -> result.exceptionOrNull()?.message
                            else -> null
                        }
                    )
                }
            })

            repository.getStockDayAll()
                .zip(repository.getStockDayAvgAll()) { stockDayAllResult, stockDayAvgAllResult ->
                    stockDayAllResult to stockDayAvgAllResult
                }.cancellable().collect({ (stockDayAllResult, stockDayAvgAllResult) ->

                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            stockDayAll = stockDayAllResult.getOrNull(),
                            stockDayAvgAll = (if (skipFilter) stockDayAvgAllResult.getOrNull() else ArrayUtils.mergeSortedArrays(
                                stockDayAllResult.getOrNull(),
                                stockDayAvgAllResult.getOrNull()
                            )),
                            error = when {
                                stockDayAllResult.isFailure -> stockDayAllResult.exceptionOrNull()?.message
                                stockDayAvgAllResult.isFailure -> stockDayAvgAllResult.exceptionOrNull()?.message
                                else -> null
                            }
                        )
                    }

                })
        }

    }
}