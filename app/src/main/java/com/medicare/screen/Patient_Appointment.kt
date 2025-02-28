package com.medicare.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalContext
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
import com.medicare.data.Profile
import com.medicare.data.Repository
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal90

@Composable
fun Patient_Appointment(){

    val uriHandler = LocalUriHandler.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val patientAppointmentList = Repository.patientAppointment.observeAsState().value

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
            if(patientAppointmentList.isNullOrEmpty()){
                Text("No previous appointments", fontSize = 20.sp, fontWeight = FontWeight.Bold,color = Teal90)
            }
            else{
                Text("Your appointments", fontSize = 20.sp, fontWeight = FontWeight.Bold,color = Teal90)
                LazyColumn{
                    items(patientAppointmentList.size){ index ->
                        SinglePatientAppointment(screenWidth,patientAppointmentList[index],uriHandler)
                    }
                }
            }
        }
    }
}

@Composable
fun SinglePatientAppointment(screenWidth: Dp,appointment: Appointment,uriHandler: UriHandler){

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
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(
                    appointment.profile.doctorName.toString(),
                    color = Teal90,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "(${appointment.profile.degree})",
                    color = Teal90,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                appointment.profile.specialization.toString(),
                color = Teal90,
                fontSize = 14.sp,
            )
            Text(
                appointment.profile.consultationFee.toString(),
                color = Teal90,
                fontSize = 14.sp,
            )
            Text(
                appointment.date.toString(),
                color = Teal90,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                appointment.time.toString(),
                color = Teal90,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                Text(
                    "Status - ",
                    color = Teal90,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    appointment.status.toString(),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.background(if(appointment.status == "Accepted") Color(0xFF4caf50)
                                                    else if(appointment.status == "Rejected") Color(0xFFf44336)
                                                    else Color.Gray).padding(10.dp)
                )
            }
            if(appointment.status == "Accepted"){
                val url1 = "https://meet.google.com/oku-pupy-hba"
                val url2 = "https://forms.gle/kNDmqoTU4iopXVDEA"
                Text("Join google meet", color = Teal90, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("https://meet.google.com/oku-pupy-hba")
                        }
                    },
                    modifier = Modifier.clickable { uriHandler.openUri(url1) }.padding(2.dp)
                )
                Text("Give feedback on link below", color = Teal90, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("https://forms.gle/kNDmqoTU4iopXVDEA")
                        }
                    },
                    modifier = Modifier.clickable { uriHandler.openUri(url2) }.padding(2.dp)
                )
            }
        }
    }
}