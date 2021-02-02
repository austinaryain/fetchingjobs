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
//                listOf(
//                    FetchItem(1, 1, "Test"),
//                    FetchItem(2, 2, "Test"),
//                    FetchItem(3, 12, null),
//                    FetchItem(4, 926, "Test"),
//                    FetchItem(5, 10, "Test"),
//                    FetchItem(6, 111, "Test"),
//                    FetchItem(7, 12, "Test"),
//                    FetchItem(8, 12, "Test"),
//                    FetchItem(9, 101, "Test"),
//                    FetchItem(1, 1, "Test"),
//                    FetchItem(2, 2, "Test"),
//                    FetchItem(3, 12, null),
//                    FetchItem(4, 926, "Example"),
//                    FetchItem(5, 10, "Omega"),
//                    FetchItem(6, 111, "Zeta"),
//                    FetchItem(7, 12, "Start"),
//                    FetchItem(8, 12, "Beta"),
//                    FetchItem(9, 101, "Alpha"),
//                    FetchItem(1, 1, "Test"),
//                    FetchItem(2, 2, "Test"),
//                    FetchItem(3, 12, null),
//                    FetchItem(4, 926, "Charlie"),
//                    FetchItem(5, 10, "Echo"),
//                    FetchItem(6, 111, "Bravo"),
//                    FetchItem(7, 12, "Delta"),
//                    FetchItem(8, 12, "Why"),
//                    FetchItem(9, 101, "Test"),
//                )
            )
        } catch (ex: Exception) {
            Either.Left(ex)
        }
    }
}
