package com.austinaryain.fetchchallenge.data.remote

import arrow.core.Either
import com.austinaryain.fetchchallenge.data.FetchRemoteDataSource
import com.austinaryain.fetchchallenge.data.models.FetchItem
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(val fetchClient: FetchClient) :
    FetchRemoteDataSource {
    override suspend fun getRemoteData(): Either<Exception, List<FetchItem>> {
        return try {
            Either.Right(
                fetchClient.getData()
            )
        } catch (ex: Exception) {
            Either.Left(ex)
        }
    }
}
