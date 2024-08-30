package com.example.jetpack10.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.jetpack10.model.GamesList
import com.example.jetpack10.util.Constants.Companion.Color_Back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title:String,showBackButton:Boolean = false,onClickBackButton:() -> Unit,onClickAction:() ->Unit){
    TopAppBar(title = { Text(text = title, color = Color.White, fontWeight = FontWeight.ExtraBold)},colors = TopAppBarDefaults.topAppBarColors(containerColor = Color_Back), navigationIcon = {
        if(showBackButton){
        IconButton(onClick = {onClickBackButton()}) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
        }
    }else{

    }},actions = {
        if (!showBackButton) {
            IconButton(onClick = { onClickAction() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    })
}

@Composable
fun MainImage(image:String){
    val imagen = rememberAsyncImagePainter(model = image)

    Image(painter = imagen, contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp))
}

@Composable
fun CardGame(game:GamesList,onClick:() ->Unit){
    Card(shape = RoundedCornerShape(5.dp), modifier = Modifier
        .padding(5.dp)
        .shadow(40.dp)
        .clickable {
            onClick()
        }) {
        Column {
            MainImage(image = game.background_image)
        }
    }
}

@Composable
fun MetaWebsite(url:String){
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    Column{
        Text(
            text = "MetaScore.",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        Button(onClick = {if(url.isEmpty()){Toast.makeText(context,"No hay sitio Web",Toast.LENGTH_SHORT).show() }else{context.startActivity(intent)}},colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Gray)) {
            Text(text = "Sitio Web")
        }
    }
}

@Composable
fun ReviewCard(metaScore:Int){
    Card (modifier = Modifier.padding(16.dp),shape = RoundedCornerShape(8.dp),colors = CardDefaults.cardColors(
        containerColor = if(metaScore < 50)Color.Red
        else if (metaScore < 75)Color.Yellow
        else if(metaScore in 75..100)Color.Green
        else Color.Gray)){
        Column (modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = metaScore.toString(), fontWeight = FontWeight.ExtraBold, fontSize = 50.sp)
        }
    }
}

@Composable
fun Loader(){
    val circleColors : List<Color> = listOf(
        Color(0xFF5851D8),Color(0xFF833AB4),Color(0xFFC13584),Color(0xFFE1306C),
        Color(0xFFFD1D1D),Color(0xFFF56040),Color(0xFFF77737),Color(0xFFFCAF45),
        Color(0xFFFFDC80),Color(0xFF5851D8)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 360, easing = LinearEasing)), label = ""
    )

    CircularProgressIndicator(
        progress = { 1f },
        modifier = Modifier.size(100.dp).rotate(degrees = rotateAnimation).
        border(width = 4.dp, brush = Brush.sweepGradient(circleColors), shape = CircleShape),
        color = MaterialTheme.colorScheme.background,
        strokeWidth = 1.dp
    )
}
