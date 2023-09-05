package com.example.newscompose.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.news.model.ResultLocal
import com.example.newscompose.ui.NewsUiState

@Composable
fun HomeScreen(
    newsUiState: NewsUiState,
    modifier: Modifier = Modifier
) {
    when (newsUiState) {
        is NewsUiState.Loading -> LoadingScreen(modifier)
        is NewsUiState.Success -> ResultGridScreen(
            resultLocalList = newsUiState.resultLocal,
            modifier = modifier
        )
        is NewsUiState.Error -> {}
    }
}