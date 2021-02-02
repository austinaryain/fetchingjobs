package com.austinaryain.fetchchallenge.domain.repository

import arrow.core.Either
import com.austinaryain.fetchchallenge.data.FetchRemoteDataSource
import com.austinaryain.fetchchallenge.data.models.DataResponse
import com.austinaryain.fetchchallenge.domain.FetchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class FetchRepositoryImpl @Inject constructor(private val remoteDataSource: FetchRemoteDataSource) :
    FetchRepository {
    override suspend fun getData(): Either<Exception, DataResponse> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getRemoteData().fold(
                {
                    Either.left(it)
                },
                { response ->
                    val filtered = response.filter { !it.name.isNullOrEmpty() }.sortedBy { it.name }
                    Either.right(DataResponse(data = filtered.groupBy { it.listId }.toSortedMap()))
                }
            )
        }
    }
}
