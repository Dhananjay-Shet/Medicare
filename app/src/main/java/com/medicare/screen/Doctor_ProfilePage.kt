package com.medicare.screen

import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal90
import com.medicare.viewmodel.DPViewmodel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun Doctor_ProfilePage(){

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val viewmodel: DPViewmodel = viewModel()
    val auth = FirebaseAuth.getInstance()
    val id = rememberSaveable { mutableStateOf("") }
    val photoUrl = rememberSaveable { mutableStateOf("") }
    val userName = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val feeMenu = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            id.value = user?.uid ?: ""
            photoUrl.value = user?.photoUrl.toString()
            email.value = user?.email ?: ""
            userName.value = user?.displayName ?: "Guest"
        }
        auth.addAuthStateListener(listener)
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text("Profile",color = Teal90, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 5.dp))
            Image(
                painter = rememberAsyncImagePainter(photoUrl.value),
                contentDescription = "Profile",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            )

            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(10.dp)){
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

                OutlinedTextField(
                    value = viewmodel.licenseNo.observeAsState().value.toString(),
                    onValueChange = {
                        viewmodel.updateLicenseNo(
                            it
                        )
                    },
                    isError = viewmodel.licenseNoError.observeAsState(
                        ""
                    ).value.isNotBlank(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            "License Number",
                            color = Color.DarkGray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.DarkGray,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = Color.DarkGray,
                        focusedTextColor = Color.DarkGray,
                        unfocusedTextColor = Color.DarkGray,
                        errorBorderColor = Color.Red,
                        errorTextColor = Color.DarkGray,
                        errorCursorColor = Color.DarkGray,
                        errorSupportingTextColor = Color.Red,
                    ),
                    modifier = Modifier.width(
                        screenWidth - 25.dp
                    ),
                )
                if (viewmodel.licenseNoError.observeAsState(
                        ""
                    ).value.isNotEmpty()
                ) {
                    Text(
                        text = viewmodel.licenseNoError.observeAsState(
                            ""
                        ).value,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = 10.dp
                        ),
                        color = Color.Red
                    )
                }

                OutlinedTextField(
                    value = viewmodel.specialization.observeAsState().value.toString(),
                    onValueChange = {
                        viewmodel.updateSpecialization(
                            it
                        )
                    },
                    isError = viewmodel.specializationError.observeAsState(
                        ""
                    ).value.isNotBlank(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            "Specialization",
                            color = Color.DarkGray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.DarkGray,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = Color.DarkGray,
                        focusedTextColor = Color.DarkGray,
                        unfocusedTextColor = Color.DarkGray,
                        errorBorderColor = Color.Red,
                        errorTextColor = Color.DarkGray,
                        errorCursorColor = Color.DarkGray,
                        errorSupportingTextColor = Color.Red,
                    ),
                    modifier = Modifier.width(
                        screenWidth - 25.dp
                    ),
                )
                if (viewmodel.specializationError.observeAsState(
                        ""
                    ).value.isNotEmpty()
                ) {
                    Text(
                        text = viewmodel.specializationError.observeAsState(
                            ""
                        ).value,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = 10.dp
                        ),
                        color = Color.Red
                    )
                }

                OutlinedTextField(
                    value = viewmodel.degree.observeAsState().value.toString(),
                    onValueChange = {
                        viewmodel.updateDegree(
                            it
                        )
                    },
                    isError = viewmodel.degreeError.observeAsState(
                        ""
                    ).value.isNotBlank(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            "Degree",
                            color = Color.DarkGray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.DarkGray,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = Color.DarkGray,
                        focusedTextColor = Color.DarkGray,
                        unfocusedTextColor = Color.DarkGray,
                        errorBorderColor = Color.Red,
                        errorTextColor = Color.DarkGray,
                        errorCursorColor = Color.DarkGray,
                        errorSupportingTextColor = Color.Red,
                    ),
                    modifier = Modifier.width(
                        screenWidth - 25.dp
                    ),
                )
                if (viewmodel.degreeError.observeAsState(
                        ""
                    ).value.isNotBlank()
                ) {
                    Text(
                        text = viewmodel.degreeError.observeAsState(
                            ""
                        ).value,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = 10.dp
                        ),
                        color = Color.Red
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        top = 5.dp
                    )
                ) {
                    Text(
                        "Consultation Fee",
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(
                            15.dp
                        )
                    )
                    ExposedDropdownMenuBox(
                        expanded = feeMenu.value,
                        onExpandedChange = {
                            feeMenu.value =
                                it
                        },
                        modifier = Modifier
                            .width(
                                140.dp
                            )
                            .height(
                                50.dp
                            )
                    ) {
                        OutlinedTextField(
                            value = viewmodel.consultationFee.observeAsState(
                                "Rs.100/-"
                            ).value,
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor(
                                    MenuAnchorType.PrimaryNotEditable
                                ),
                            readOnly = true,
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    Icons.Filled.ArrowDropDown,
                                    ""
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedTextColor = Color.DarkGray,
                                unfocusedTextColor = Color.DarkGray,
                                unfocusedIndicatorColor = Color.LightGray,
                                focusedIndicatorColor = Color.DarkGray,
                                unfocusedContainerColor = Color.LightGray,
                                focusedContainerColor = Color.LightGray,
                            ),
                        )
                        ExposedDropdownMenu(
                            expanded = feeMenu.value,
                            onDismissRequest = {
                                feeMenu.value =
                                    false
                            },
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Rs.100/-",
                                        color = Color.DarkGray
                                    )
                                },
                                onClick = {
                                    viewmodel.updateConsultationFee(
                                        "Rs.100/-"
                                    )
                                    feeMenu.value =
                                        false
                                },
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Rs.200/-",
                                        color = Color.DarkGray
                                    )
                                },
                                onClick = {
                                    viewmodel.updateConsultationFee(
                                        "Rs.200/-"
                                    )
                                    feeMenu.value =
                                        false
                                },
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Rs.300/-",
                                        color = Color.DarkGray
                                    )
                                },
                                onClick = {
                                    viewmodel.updateConsultationFee(
                                        "Rs.300/-"
                                    )
                                    feeMenu.value =
                                        false
                                },
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Rs.500/-",
                                        color = Color.DarkGray
                                    )
                                },
                                onClick = {
                                    viewmodel.updateConsultationFee(
                                        "Rs.500/-"
                                    )
                                    feeMenu.value =
                                        false
                                },
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Rs.1000/-",
                                        color = Color.DarkGray
                                    )
                                },
                                onClick = {
                                    viewmodel.updateConsultationFee(
                                        "Rs.1000/-"
                                    )
                                    feeMenu.value =
                                        false
                                },
                            )
                        }
                    }
                }
                Spacer(Modifier.height(25.dp))
                Row{
                    Column{
                        Button(
                            onClick = {
                                viewmodel.resetSlot(message = { msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                })
                            },
                            modifier = Modifier
                                .padding(
                                    5.dp
                                )
                                .width(
                                    150.dp
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Teal10,
                                contentColor = Teal90
                            )
                        ) {
                            Text(
                                "Reset Slot",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Use only once per day in morning",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            viewmodel.saveProfile(id.value,userName.value,message = { bool,msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                if(bool){
                                    dispatcher?.onBackPressed()
                                }
                            })
                        },
                        modifier = Modifier
                            .padding(
                                5.dp
                            )
                            .width(
                                150.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Teal10,
                            contentColor = Teal90
                        )
                    ) {
                        Text(
                            "Save Changes",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(25.dp))

            }
        }
    }
}
