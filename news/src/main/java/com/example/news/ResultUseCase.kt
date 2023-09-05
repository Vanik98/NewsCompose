package com.example.news

import com.example.news.model.ResultLocal
import com.example.news.net.response.Result
import com.example.news.repository.NewsShearedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface NewsUsesCases {

    interface GetAllResultsUseCase {
        fun execute(page: Int): Flow<List<ResultLocal>>
    }

    interface GetNetResultUseCase {
        fun execute(page: Int): Flow<List<Result>?>
    }

    interface GetFavoriteResultUseCase {
        fun execute(): Flow<List<ResultLocal>?>
    }

    interface DeleteFavoriteResultUseCase {
        suspend fun execute(resultLocal: ResultLocal)
    }


    interface SaveFavoriteResultUseCase {
        suspend fun execute(resultLocal: ResultLocal)
    }

}

internal class GetAllResultsUseCaseImpl(
    private val getNetResults: NewsUsesCases.GetNetResultUseCase,
    private val getFavoriteResult: NewsUsesCases.GetFavoriteResultUseCase
) : NewsUsesCases.GetAllResultsUseCase {
    private var saveDbResult: List<ResultLocal>? = null
    private var showResult = arrayListOf<ResultLocal>()

    override fun execute(page: Int) =
        combine(getNetResults.execute(page), getFavoriteResult.execute()) { netResults, dBResults ->
            val filterResultLocal: List<ResultLocal>
            if (page == 1) {
                filterResultLocal = FilterLogic.filterResults(dBResults, netResults, true)
                saveDbResult = dBResults
            } else {
                filterResultLocal = FilterLogic.filterResults(saveDbResult, netResults, false)
                showResult.addAll(filterResultLocal)
            }
            showResult.addAll(filterResultLocal)
            filterResultLocal
        }


}

internal class GetNetResultUseCaseImpl(private val repository: NewsShearedRepository) : NewsUsesCases.GetNetResultUseCase {
    override fun execute(page: Int) = repository.getNetResults(page)
}

internal class GetFavoriteResultUseCaseImpl(private val repository: NewsShearedRepository) : NewsUsesCases.GetFavoriteResultUseCase {
    override fun execute() = repository.getDbResultsLocal()
}

internal class DeleteFavoriteResultUseCaseImpl(private val repository: NewsShearedRepository) : NewsUsesCases.DeleteFavoriteResultUseCase {
    override suspend fun execute(resultLocal: ResultLocal) = repository.deleteFavoriteResulLocal(resultLocal)
}

internal class SaveFavoriteResultUseCaseImpl(private val repository: NewsShearedRepository) : NewsUsesCases.SaveFavoriteResultUseCase {
    override suspend fun execute(resultLocal: ResultLocal) = repository.saveFavoriteResultLocal(resultLocal)
}