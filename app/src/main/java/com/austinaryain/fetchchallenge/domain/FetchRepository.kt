package com.austinaryain.fetchchallenge.domain

import arrow.core.Either
import com.austinaryain.fetchchallenge.data.models.DataResponse
import java.lang.Exception

interface FetchRepository {
    suspend fun getData(): Either<Exception, DataResponse>
}
