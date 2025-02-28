package com.medicare.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicare.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(nextPage: () -> Unit){

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    //val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(Unit){
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            nextPage()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Image(painter = painterResource(R.drawable.splash_dr), contentDescription = "Dr", modifier = Modifier.height(screenWidth-25.dp).width(screenWidth-25.dp))
            Text("Welcome to Medicare",modifier = Modifier.padding(top=15.dp),fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }


}