package com.example.newscompose

import android.app.Application
import com.example.news.di.newsModules
import com.example.newscompose.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@NewsComposeApp)
            modules(newsModules)
            modules(appModules)
        }
    }

}