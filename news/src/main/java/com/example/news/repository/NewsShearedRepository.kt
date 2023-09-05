package com.example.news.repository

import com.example.news.net.response.News
import com.example.news.net.NewsApiService
import com.example.news.net.response.Result
import com.example.news.room.dao.ResultDao
import com.example.news.model.ResultLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal interface NewsShearedRepository {
    fun getNetResults(page: Int): Flow<List<Result>?>
    fun getDbResultsLocal(): Flow<List<ResultLocal>?>
    suspend fun saveFavoriteResultLocal(resultLocal: ResultLocal)
    suspend fun deleteFavoriteResulLocal(resultLocal: ResultLocal)
}

internal class NewsShearedRepositoryImpl(
    private val apiService: NewsApiService,
    private val resultDao: ResultDao
) : NewsShearedRepository {
    override  fun getNetResults(page: Int)= flow {
        val response = apiService.getNews(page = page)
        if (response.isSuccessful) {
            val newsJson = response.body().toString()
            val json = Json { ignoreUnknownKeys = true }
            val news: News = json.decodeFromString(newsJson)
            emit(news.response?.results)
        } else {
            emit(null)
        }
    }

    override fun getDbResultsLocal() = flow { emit(resultDao.getAll()) }.flowOn(Dispatchers.IO)

    override suspend fun saveFavoriteResultLocal(resultLocal: ResultLocal) = withContext(Dispatchers.IO) {
        resultDao.insert(resultLocal)
    }

    override suspend fun deleteFavoriteResulLocal(resultLocal: ResultLocal) = withContext(Dispatchers.IO) {
        resultDao.delete(resultLocal)
    }

}