package com.example.jetpack10.model

data class GamesModel(val count:Int,
                      val results:List<GamesList>)

data class GamesList(val id:Int,
                     val name:String,
                     val background_image:String)
