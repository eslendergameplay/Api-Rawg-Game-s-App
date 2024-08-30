package com.example.jetpack10.di

import com.example.jetpack10.data.ApiGames
import com.example.jetpack10.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesApiGames(retrofit:Retrofit):ApiGames{
        return retrofit.create(ApiGames::class.java)
    }

}