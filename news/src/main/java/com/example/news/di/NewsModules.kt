package com.example.news.di

import androidx.room.Room
import com.example.news.DeleteFavoriteResultUseCaseImpl
import com.example.news.GetAllResultsUseCaseImpl
import com.example.news.GetFavoriteResultUseCaseImpl
import com.example.news.GetNetResultUseCaseImpl
import com.example.news.NewsUsesCases
import com.example.news.SaveFavoriteResultUseCaseImpl
import com.example.news.net.NewsApiService
import com.example.news.net.NetConstants
import com.example.news.net.result.ResultCallAdapterFactory
import com.example.news.repository.NewsShearedRepository
import com.example.news.repository.NewsShearedRepositoryImpl
import com.example.news.room.AppDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit


val newsModules by lazy {
    listOf(
        useCaseModules,
        repositoryModule,
        roomModule,
        retrofitModule
    )
}

private val useCaseModules = module {
    single<NewsUsesCases.GetAllResultsUseCase> { GetAllResultsUseCaseImpl(get(), get()) }
    single<NewsUsesCases.GetNetResultUseCase> { GetNetResultUseCaseImpl(get()) }
    single<NewsUsesCases.GetFavoriteResultUseCase> { GetFavoriteResultUseCaseImpl(get()) }
    single<NewsUsesCases.SaveFavoriteResultUseCase> { SaveFavoriteResultUseCaseImpl(get()) }
    single<NewsUsesCases.DeleteFavoriteResultUseCase> { DeleteFavoriteResultUseCaseImpl(get()) }
}

private val repositoryModule = module {
    single<NewsShearedRepository> { NewsShearedRepositoryImpl(get(), get()) }
}

private val roomModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, NetConstants.NEWS_DATABASE_NAME).build() }
    single { get<AppDatabase>().ResultDao() }
}

private val retrofitModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(NetConstants.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(NetConstants.CONVERT_FACTORY.toMediaType()))
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
            .create(NewsApiService::class.java)
    }
}

