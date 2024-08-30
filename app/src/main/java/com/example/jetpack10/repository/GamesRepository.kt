package com.example.jetpack10.repository

import com.example.jetpack10.data.ApiGames
import com.example.jetpack10.model.GamesList
import com.example.jetpack10.model.GamesModel
import com.example.jetpack10.model.SingleGameModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class GamesRepository @Inject constructor(private val apiGames:ApiGames) {

    suspend fun getGames():List<GamesList>?{
        val response = apiGames.getGames()
        if(response.isSuccessful){
            return response.body()?.results
        }else{
            return null
        }
    }

    suspend fun getGamesPaging(page:Int,pageSize:Int):GamesModel{
        delay(1500)
        return apiGames.getGamesPaging(page,pageSize)
    }

    suspend fun getGameById(id:Int):SingleGameModel?{
        val response = apiGames.getGameById(id)
        if (response.isSuccessful){
            return response.body()
        }else{
            return null
        }
    }

    suspend fun getGameByName(name:String):SingleGameModel?{
        val response = apiGames.getGameByName(name)
        if(response.isSuccessful){
            return response.body()
        }else{
            return null
        }
    }
}