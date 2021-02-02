package com.austinaryain.fetchchallenge.di

import com.austinaryain.fetchchallenge.data.FetchRemoteDataSource
import com.austinaryain.fetchchallenge.data.remote.FetchClient
import com.austinaryain.fetchchallenge.data.remote.RemoteDataSourceImpl
import com.austinaryain.fetchchallenge.domain.FetchRepository
import com.austinaryain.fetchchallenge.domain.repository.FetchRepositoryImpl
import com.austinaryain.fetchchallenge.util.toMediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideFetchClient(retrofit: Retrofit): FetchClient {
        return retrofit.create(FetchClient::class.java)
    }

    @Provides
    @Singleton
    fun provideFetchDataSource(client: FetchClient): FetchRemoteDataSource {
        return RemoteDataSourceImpl(client)
    }

    @Provides
    @Singleton
    fun provideFetchRepository(remoteDataSource: FetchRemoteDataSource): FetchRepository {
        return FetchRepositoryImpl(remoteDataSource)
    }
}
