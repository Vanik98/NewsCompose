package com.example.newscompose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewModel by viewModel<NewsViewModel>()

    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsComposeTheme {
                NewsApp(viewModel)
            }
        }
    }
}
