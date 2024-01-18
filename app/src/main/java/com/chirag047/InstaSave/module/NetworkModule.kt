package com.chirag047.InstaSave.module

import com.chirag047.InstaSave.Common.API_BASE_URL
import com.chirag047.InstaSave.api.InstaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getMemeApi(retrofit: Retrofit): InstaApi {
        return retrofit.create(InstaApi::class.java)
    }
}