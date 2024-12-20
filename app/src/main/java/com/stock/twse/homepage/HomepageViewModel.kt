package com.stock.twse.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stock.twse.data.BwibbuAll
import com.stock.twse.data.BwibbuInfo
import com.stock.twse.network.ITravelRepository
import com.stock.twse.network.TwseRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch

data class HomepageUiState(
    val isLoading: Boolean = true,
    val bwibbuAll: BwibbuAll? = null,
    val error: String? = null,
)

class HomepageViewModel(
    private val repository: ITravelRepository = TwseRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomepageUiState())
    val uiState: StateFlow<HomepageUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null
    fun fetchData() {
        fetchJob?.cancelChildren()

        fetchJob = viewModelScope.launch {
            repository.getBwibbuAll().collect({ result ->

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
        }

    }
}