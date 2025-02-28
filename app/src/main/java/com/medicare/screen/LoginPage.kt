package com.medicare.screen

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.medicare.R
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal90
import com.medicare.viewmodel.LoginViewmodel

@Composable
fun LoginPage(homePage: (Boolean) -> Unit){

    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val viewmodel : LoginViewmodel = viewModel()
    val isUserDoctor = viewmodel.isUserDoctor.observeAsState(true).value
    val loading = rememberSaveable { mutableStateOf(false) }

    val signInLauncher =
        rememberLauncherForActivityResult(FirebaseAuthUIActivityResultContract()) { result ->
            loading.value = false
            if (result.resultCode == RESULT_OK) {
                homePage(isUserDoctor)
                viewmodel.setHomePage(context.applicationContext)
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            } else if (result.resultCode == RESULT_CANCELED) {
                Toast.makeText(context, "Login was cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize(),contentAlignment = Alignment.Center){
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Column(horizontalAlignment = Alignment.Start,modifier = Modifier.padding(top=screenHeight/8)){
                    Text("Choose your role : ", modifier = Modifier.padding(10.dp),fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top=10.dp)){
                        RadioButton(
                            selected = isUserDoctor,
                            onClick = { viewmodel.isUserDoctor.value = true },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.DarkGray,
                                unselectedColor = Color.LightGray,
                            )
                        )
                        Text(
                            "Doctor",
                            color = Color.DarkGray,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end=10.dp)
                        )
                        Image(painter = painterResource(R.drawable.doctor), contentDescription = "Dr", modifier = Modifier.width(50.dp))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(top=10.dp)){
                        RadioButton(
                            selected = !isUserDoctor,
                            onClick = {  viewmodel.isUserDoctor.value = false },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.DarkGray,
                                unselectedColor = Color.LightGray
                            )
                        )
                        Text("Patient", color = Color.DarkGray, fontSize = 20.sp,modifier = Modifier.padding(end=10.dp))
                        Image(painter = painterResource(R.drawable.patient), contentDescription = "patient", modifier = Modifier.width(50.dp))
                    }
                }
                Button(
                    onClick = {
                        loading.value = true
                        signInLauncher.launch(viewmodel.getSignInIntent())
                    }, modifier = Modifier
                        .padding(top = 30.dp)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Teal10,
                        contentColor = Teal90
                    )
                ) {
                    Image(painter = painterResource(R.drawable.google), contentDescription = "google", modifier = Modifier.width(35.dp).padding(end=10.dp))
                    Text("Sign In with Google", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

            }
            if (loading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.width(50.dp),
                    color = Color.Gray,
                    trackColor = Color.Unspecified,
                )
            }
        }
    }
}
