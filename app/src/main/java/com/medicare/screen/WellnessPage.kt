package com.medicare.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicare.data.InfoData
import com.medicare.ui.theme.Teal90

@Composable
fun WellnessPage(){

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var wellnessList = rememberSaveable { mutableStateOf(emptyList<Pair<String, String>>()) }
    LaunchedEffect(Unit) {
        wellnessList.value=InfoData.getWellnessList()
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    innerPadding
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Wellness Section", color = Teal90, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
            LazyColumn{
                items(wellnessList.value.size){ index ->
                    val (heading,content) = wellnessList.value[index]
                    SingleCard(screenWidth,heading,content)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleCard(screenWidth:Dp,heading:String,content: String){

    val isExpanded = rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().width(screenWidth).padding(5.dp)){
        Row(modifier = Modifier.clickable{ isExpanded.value = !isExpanded.value }.width(screenWidth),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(isExpanded.value==false)
                Icon(imageVector = Icons.Filled.Add, contentDescription = ""  , tint = Color.DarkGray, modifier = Modifier.size(40.dp))
            else
                Icon(imageVector = Icons.Filled.Remove, contentDescription = ""  , tint = Color.DarkGray,modifier = Modifier.size(40.dp))
            Text(heading,color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        if(isExpanded.value)
        {
            Text(content,color = Color.DarkGray, fontSize = 18.sp, modifier = Modifier.padding(start=15.dp))
        }
    }



}