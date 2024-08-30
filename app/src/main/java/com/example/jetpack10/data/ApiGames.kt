package com.example.jetpack10.data

import com.example.jetpack10.model.GamesModel
import com.example.jetpack10.model.SingleGameModel
import com.example.jetpack10.util.Constants
import com.example.jetpack10.util.Constants.Companion.API_KEY
import com.example.jetpack10.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiGames{

    @GET(ENDPOINT + API_KEY)
    suspend fun getGames(): Response<GamesModel>

    @GET(ENDPOINT + API_KEY)
    suspend fun getGamesPaging(@Query ("page")page:Int,@Query ("page_size")pageSize:Int):GamesModel

    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id")id :Int):Response<SingleGameModel>

    @GET("$ENDPOINT/{name}$API_KEY")
    suspend fun getGameByName(@Path(value = "name")name:String):Response<SingleGameModel>
}