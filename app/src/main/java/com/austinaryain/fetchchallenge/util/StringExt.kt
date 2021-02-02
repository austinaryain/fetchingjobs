package com.austinaryain.fetchchallenge.util

import okhttp3.MediaType

fun String.toMediaType(): MediaType {
    return MediaType.parse(this)!!
}
