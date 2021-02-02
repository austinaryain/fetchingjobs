package com.austinaryain.fetchchallenge.data

import arrow.core.Either
import com.austinaryain.fetchchallenge.data.models.FetchItem

interface FetchRemoteDataSource {
    suspend fun getRemoteData(): Either<Exception, List<FetchItem>>
}
