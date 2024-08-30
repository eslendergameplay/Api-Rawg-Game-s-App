package com.example.jetpack10.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpack10.components.CardGame
import com.example.jetpack10.components.Loader
import com.example.jetpack10.components.MainTopBar
import com.example.jetpack10.util.Constants.Companion.Color_Back
import com.example.jetpack10.viewModels.GamesViewModel
import kotlin.math.sin

@Composable
fun HomeView(viewModel:GamesViewModel,navC:NavController){
    Scaffold (topBar = { MainTopBar(title = "Api Games.", onClickBackButton = {}) {
        navC.navigate("SearchGameView")
    }}) {
        ContentHomeview(it,viewModel,navC)
    }
}
@Composable
fun ContentHomeview(padding:PaddingValues,viewModel: GamesViewModel,navC:NavController){
    val games by viewModel.games.collectAsState()
    var search by remember { mutableStateOf("")}
    val gamesPage = viewModel.gamesPage.collectAsLazyPagingItems()
    Column ( modifier = Modifier.padding(padding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = search, onValueChange = {search = it}, label = { Text(text = "Buscar Juegos.")}, keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done), keyboardActions = KeyboardActions(onDone = {
            val zero = 0
            navC.navigate("DetailView/${zero}/?${search}")
        }), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
            singleLine = true)
        LazyColumn (modifier = Modifier
            .background(Color_Back)){
            items(gamesPage.itemCount){ index->
                val item = gamesPage[index]

                if(item != null){
                    CardGame(game = item) {
                        navC.navigate("DetailView/${item.id}/?${search}")
                    }
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }

            when(gamesPage.loadState.append){
                is LoadState.NotLoading->{Unit}
                LoadState.Loading->{
                    item {
                        Column (modifier = Modifier.fillParentMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                            Loader()
                        }
                    }
                }
                is LoadState.Error->{
                    item {
                        Text(text = "Error al Cargar.")
                    }
                }
            }
        /*
        LazyColumn (modifier = Modifier
            .background(Color_Back)){
            items(games){ item->
                CardGame(game = item) {
                    navC.navigate("DetailView/${item.id}/?${search}")
                }
                Text(
                    text = item.name,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

         */
        }
    }
}