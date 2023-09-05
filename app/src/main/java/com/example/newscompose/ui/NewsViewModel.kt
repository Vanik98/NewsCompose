package com.example.newscompose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.NewsUsesCases
import com.example.news.model.ResultLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface NewsUiState {
    data class Success(val resultLocal: List<ResultLocal>) : NewsUiState
    object Error : NewsUiState
    object Loading : NewsUiState
}

class NewsViewModel(
    private val getAllResultsUseCase: NewsUsesCases.GetAllResultsUseCase,
    private val saveFavoriteResult: NewsUsesCases.SaveFavoriteResultUseCase,
    private val deleteFavoriteResult: NewsUsesCases.DeleteFavoriteResultUseCase,
) : ViewModel() {
    private var pageCount = 0

    init {
        getResults()
    }

    var newsUiState:NewsUiState by mutableStateOf(NewsUiState.Loading)
        private set

    private val _resultFlow = MutableStateFlow<List<ResultLocal>?>(null)
    val resultFlow: StateFlow<List<ResultLocal>?>
        get() = _resultFlow

    fun getResults() {
        pageCount++
        viewModelScope.launch {
            getAllResultsUseCase.execute(pageCount).collect{
                newsUiState =NewsUiState.Success(it)
            }
        }
    }

    fun saveResult(resultLocal: ResultLocal) = viewModelScope.launch { saveFavoriteResult.execute(resultLocal) }

    fun deleteResult(resultLocal: ResultLocal) = viewModelScope.launch { deleteFavoriteResult.execute(resultLocal) }
}