package com.medicare.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal90

@Composable
fun GuidelinesPage() {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var guidelinesList = rememberSaveable { mutableStateOf(emptyList<Pair<String, String>>()) }
    LaunchedEffect(Unit) {
        guidelinesList.value=InfoData.getGuidelines()
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
            Text(" Telemedicine Health Guidelines", color = Teal90, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
            LazyColumn{
                items(guidelinesList.value.size){ index ->
                    val (heading,content) = guidelinesList.value[index]
                    SingleGuideline(screenWidth,heading,content)
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleGuideline(screenWidth:Dp,heading:String,content: String){

    val isExpanded = rememberSaveable { mutableStateOf(false) }

    Card(modifier = Modifier.padding(5.dp).width(screenWidth-25.dp)
            .clickable{ isExpanded.value = !isExpanded.value },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().background(Teal10).padding(10.dp),){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                if(isExpanded.value==false)
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = ""  , tint = Color.DarkGray, modifier = Modifier.size(50.dp))
                else
                    Icon(imageVector = Icons.Filled.ArrowDropUp, contentDescription = ""  , tint = Color.DarkGray,modifier = Modifier.size(50.dp))

                Text(heading,color = Teal90, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            if(isExpanded.value)
            {
               Text(content,color = Teal90, fontSize = 16.sp, modifier = Modifier.padding(start=15.dp))
            }
        }
    }
}