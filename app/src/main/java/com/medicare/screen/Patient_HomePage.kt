package com.medicare.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicare.R
import com.medicare.data.FirebaseInstance
import com.medicare.data.Repository
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal90

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Patient_HomePage(logout : () -> Unit, profile: () -> Unit, book: () -> Unit, appointment: () -> Unit, wellness : () -> Unit) {

    val context = LocalContext.current
    val expanded = rememberSaveable { mutableStateOf(false) }
    val currentUser = Repository.getCurrentUser()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(Unit) {
        Repository.retrieveProfile()
        Repository.getPatientAppointment()
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent,
        topBar = {
            TopAppBar(
                title = {Row(verticalAlignment = Alignment.CenterVertically){Text("Hello, ${currentUser?.displayName ?: "Guest"}", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    Icon(painter = painterResource(R.drawable.healthy), contentDescription = "Health Icon",modifier = Modifier.size(30.dp).padding(start=5.dp),tint = Color.Unspecified)}},
                actions = {
                    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(5.dp)){
                        IconButton(onClick = { expanded.value=true }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = null,modifier = Modifier.size(50.dp), tint = Teal90)
                        }
                        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value=false }) {
                            DropdownMenuItem(text = { Text("Logout")}, modifier = Modifier.width(screenWidth/2.5f),
                                onClick = { FirebaseInstance.logoutUser(context, onLogoutComplete = {
                                logout()
                                Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show()
                            })})
                        }
                    }
                },
            )
        },
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("🌿 Eat healthy, stay healthy. Choose fresh fruits & veggies! 🥗🍎",
                modifier = Modifier.basicMarquee( iterations = Int. MAX_VALUE, repeatDelayMillis = 0, velocity = 40.dp ),
                color = Color.DarkGray)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(4) { index ->
                    if(index==0){ SingleGridItem("Profile",screenWidth,R.drawable.profile,onClick = { profile()  }) }
                    else if(index==1){ SingleGridItem("Book Appointments",screenWidth,R.drawable.patient_appointment,onClick = { book() }) }
                    else if(index==2){ SingleGridItem("My Appointments",screenWidth,R.drawable.schedule,onClick = { appointment() }) }
                    else { SingleGridItem("Wellness",screenWidth,R.drawable.wellness,onClick = { wellness() }) }
                }
            }

        }
    }
}

@Composable
fun SingleGridItem(
    name : String,screenWidth: Dp,id:Int,onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(
                screenWidth / 2
            )
            .height(
                screenWidth / 2
            )
            .padding(5.dp)
            .clickable{onClick()},
        elevation = CardDefaults.cardElevation(
            5.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Teal10).padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id), contentDescription = "", modifier = Modifier.width(screenWidth/3).height(screenWidth/3))
            Spacer(modifier = Modifier.height(10.dp))
            Text(name,color = Teal90, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}