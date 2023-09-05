package com.example.newscompose.di

import com.example.newscompose.ui.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules by lazy {
    listOf(
        newsModule
    )
}

private val newsModule = module {
    viewModel {
        NewsViewModel(get(), get(), get())
    }
}