package com.medicare.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicare.data.Appointment
import com.medicare.data.Approval
import com.medicare.data.Repository
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal90

@Composable
fun Doctor_Appointment(){

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val doctorAppointmentList = Repository.doctorAppointment.observeAsState().value
    val uriHandler = LocalUriHandler.current

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
            if(doctorAppointmentList.isNullOrEmpty()){
                Text("No previous appointments", fontSize = 20.sp, fontWeight = FontWeight.Bold,color = Teal90)
            }
            else{
                Text("Your appointments", fontSize = 20.sp, fontWeight = FontWeight.Bold,color = Teal90)
                LazyColumn{
                    items(doctorAppointmentList.size){ index ->
                        SingleDoctorAppointment(screenWidth,doctorAppointmentList[index],uriHandler)
                    }
                }
            }
        }
    }
}

@Composable
fun SingleDoctorAppointment(screenWidth: Dp,approval: Approval,uriHandler: UriHandler){

    Card(
        modifier = Modifier
            .width(
                screenWidth
            )
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(
            1.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Teal10).padding(10.dp)
        ) {
            Text(approval.patientName, color = Teal90, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(approval.date, color = Teal90, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(approval.time, color = Teal90, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            val url1 = "https://meet.google.com/oku-pupy-hba"
            Text("Join google meet", color = Teal90, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("https://meet.google.com/oku-pupy-hba")
                    }
                },
                modifier = Modifier.clickable { uriHandler.openUri(url1) }.padding(2.dp)
            )
        }
    }
}