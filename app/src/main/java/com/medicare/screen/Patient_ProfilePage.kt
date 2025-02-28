package com.medicare.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.medicare.ui.theme.Teal90

@Composable
fun Patient_ProfilePage(){

    val auth = FirebaseAuth.getInstance()
    val photoUrl =
        rememberSaveable { mutableStateOf("") }
    val userName = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            photoUrl.value = user?.photoUrl.toString()
            email.value = user?.email ?: ""
            userName.value = user?.displayName ?: "Guest"
        }
        auth.addAuthStateListener(listener)
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            ){
            Text("Profile",color = Teal90, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 5.dp))
            Image(
                painter = rememberAsyncImagePainter(photoUrl.value),
                contentDescription = "Profile",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.DarkGray, CircleShape),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            )

            OutlinedTextField(
                value = userName.value,
                onValueChange = {

                },
                readOnly = true,
                singleLine = true,
                label = { Text("Name", color = Color.Black) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.DarkGray,
                ),
                modifier = Modifier.width(screenWidth-25.dp).padding(top = 5.dp),
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = {

                },
                readOnly = true,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = "Email",
                        tint = Color.DarkGray
                    )
                },
                singleLine = true,
                label = { Text("Email", color = Color.Black) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.DarkGray,
                ),
                modifier = Modifier
                    .width(screenWidth-25.dp),
            )
            Spacer(Modifier.height(25.dp))


        }

    }
}