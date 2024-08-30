package com.example.jetpack10.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpack10.components.MainImage
import com.example.jetpack10.components.MainTopBar
import com.example.jetpack10.components.MetaWebsite
import com.example.jetpack10.components.ReviewCard
import com.example.jetpack10.util.Constants.Companion.Color_Back
import com.example.jetpack10.viewModels.GamesViewModel

@Composable
fun DetailView(viewModel: GamesViewModel,navC: NavController,id:Int,name:String?){
    LaunchedEffect(Unit) {
        if(id == 0){
            name.let {
                if (it != null) {
                    viewModel.getGameByName(it.replace(" ","-"))
                }
            }
        }else{
            viewModel.getGameById(id)
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clean()
        }
    }

    Scaffold (topBar = { MainTopBar(title = viewModel.state.name,
        showBackButton = true, onClickBackButton = {navC.popBackStack()}) {}}){
        ContentDetailView(it,viewModel,navC)
    }
}

@Composable
fun ContentDetailView(padding:PaddingValues,viewModel: GamesViewModel,navC:NavController){
    val state = viewModel.state
    Column (modifier = Modifier
        .padding(padding)
        .background(Color_Back)){
        MainImage(image = state.background_image)
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 5.dp)){
            MetaWebsite(url = state.website)
            ReviewCard(metaScore = state.metacritic)
        }
        val scroll = rememberScrollState(0)
        Text(text = state.description_raw,color = Color.White,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 15.dp,end = 15.dp, bottom = 20.dp).verticalScroll(scroll))
    }
}