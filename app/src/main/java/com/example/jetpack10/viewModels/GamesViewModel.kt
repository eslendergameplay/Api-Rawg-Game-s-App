package com.example.jetpack10.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.jetpack10.data.GamesDataSource
import com.example.jetpack10.model.GamesList
import com.example.jetpack10.repository.GamesRepository
import com.example.jetpack10.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val reposi:GamesRepository) : ViewModel() {
    private val _games = MutableStateFlow<List<GamesList>>(emptyList())
    val games = _games.asStateFlow()
    var state by mutableStateOf(GameState())
        private set

    val gamesPage = Pager(PagingConfig(pageSize = 6)){
        GamesDataSource(reposi)
    }.flow.cachedIn(viewModelScope)

    init {
        fetchGames()
    }

    private fun fetchGames(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = reposi.getGames()
                _games.value = result ?: emptyList()
            }
        }
    }

    fun getGameById(id:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = reposi.getGameById(id)

                state = state.copy(
                    name = result?.name ?: "",
                    description_raw = result?.description_raw ?: "",
                    metacritic = result?.metacritic ?: 111,
                    website = result?.website ?: "sin web",
                    background_image = result?.background_image ?: ""
                )
            }
        }
    }

    fun getGameByName(name:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = reposi.getGameByName(name)
                state = state.copy(
                    name = result?.name ?:"",
                    description_raw = result?.description_raw ?:"",
                    metacritic = result?.metacritic ?: 111,
                    website = result?.website ?: "sin web",
                    background_image = result?.background_image ?: ""
                )
            }
        }
    }

    fun clean(){
        state = state.copy(
            name = "",
            description_raw = "",
            metacritic = 0,
            website = "",
            background_image = ""
        )
    }
}