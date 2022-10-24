package com.austinaryain.fetchchallenge.data.models

import kotlinx.serialization.Serializable

data class DataResponse(
    val data: List<FetchItem>
)

@Serializable
data class FetchItem(
    val id: Int,
    val listId: Int,
    val name: String?
)
