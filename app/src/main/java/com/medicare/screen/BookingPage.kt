package com.medicare.screen

import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicare.data.Profile
import com.medicare.data.Repository
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal20
import com.medicare.ui.theme.Teal30
import com.medicare.ui.theme.Teal40
import com.medicare.ui.theme.Teal90

@Composable
fun BookingPage() {

    val context = LocalContext.current
    val profileList = Repository.profileList.observeAsState().value
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

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
            if(profileList.isNullOrEmpty()){
                Text("Doctors haven't posted yet", fontSize = 20.sp, fontWeight = FontWeight.Bold,color = Teal90)
            }
            else{
                LazyColumn {
                    items(profileList.size){ index ->
                        SingleProfile(screenWidth,profileList[index],bookingSlot = { slot,time ->
                            Repository.requestAppointment(profileList[index],slot,time,message = { bool, msg ->
                                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
                                if(bool){
                                    dispatcher?.onBackPressed()
                                }
                            })
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun SingleProfile(screenWidth: Dp,profile: Profile, bookingSlot: (String, String) -> Unit ){

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
                    profile.doctorName.toString(),
                    color = Teal90,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "(${profile.degree})",
                    color = Teal90,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                profile.specialization.toString(),
                color = Teal90,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                profile.consultationFee.toString(),
                color = Teal90,
                fontSize = 14.sp
            )

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text("09:00 AM - 10:00 AM",fontSize = 14.sp,fontWeight = FontWeight.Bold)
                if(profile.slot1){
                Button(onClick = { bookingSlot("slot1","09:00 AM - 10:00 AM")  }, modifier = Modifier.width(125.dp), colors = ButtonDefaults.buttonColors(contentColor =Color.Black, containerColor = Teal40))
                {Text("Book Slot",fontSize = 14.sp, fontWeight = FontWeight.Bold)}}
                else{
                    Button(onClick = {   }, modifier = Modifier.width(125.dp), colors = ButtonDefaults.buttonColors(contentColor =Color.Black, containerColor = Color(0xFFf44336)))
                    {Text("Slot Booked",fontSize = 14.sp, fontWeight = FontWeight.Bold)}
                }
            }


            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text("10:00 AM - 11:00 AM",fontSize = 14.sp,fontWeight = FontWeight.Bold)
                if(profile.slot2){
                Button(onClick = { bookingSlot("slot2","10:00 AM - 11:00 AM")  },modifier = Modifier.width(125.dp),colors = ButtonDefaults.buttonColors(contentColor =Color.Black, containerColor = Teal40 ))
                {Text("Book Slot",fontSize = 14.sp, fontWeight = FontWeight.Bold)}}
                else{ Button(onClick = {   },modifier = Modifier.width(125.dp),colors = ButtonDefaults.buttonColors(contentColor =Color.Black, containerColor = Color(0xFFf44336) ))
                {Text("Slot Booked",fontSize = 14.sp, fontWeight = FontWeight.Bold)}
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text("11:00 AM - 12:00 PM",fontSize = 14.sp,fontWeight = FontWeight.Bold)
                if(profile.slot3){
                Button(onClick = { bookingSlot("slot3","11:00 AM - 12:00 PM")  },modifier = Modifier.width(125.dp),colors = ButtonDefaults.buttonColors(contentColor =Color.Black, containerColor = Teal40 ))
                {Text("Book Slot",fontSize = 14.sp, fontWeight = FontWeight.Bold)}}
                else{Button(onClick = {  },modifier = Modifier.width(125.dp),colors = ButtonDefaults.buttonColors(contentColor =Color.Black, containerColor = Color(0xFFf44336)))
                {Text("Slot Booked",fontSize = 14.sp, fontWeight = FontWeight.Bold)}}
            }

        }
    }
}
