package com.example.jetpack10

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack10.navigation.NavManager
import com.example.jetpack10.ui.theme.JetPack10Theme
import com.example.jetpack10.viewModels.GamesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel :GamesViewModel by viewModels()

        setContent {
            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { isGrantedpermissions->
            }

            LaunchedEffect(Unit) {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE))

            }
            val granted = ContextCompat.checkSelfPermission(LocalContext.current,Manifest.permission.INTERNET )== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                LocalContext.current,Manifest.permission.ACCESS_NETWORK_STATE)== PackageManager.PERMISSION_GRANTED
            JetPack10Theme {
                Surface (modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {
                   if(granted){
                       NavManager(viewModel)
                   }
                }
            }
        }
    }
}



//https://api.rawg.io/api/games?key=5cc1ec96fef14809a814b47d11075b42

