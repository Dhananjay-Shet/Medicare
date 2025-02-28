package com.medicare.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicare.data.Approval
import com.medicare.data.Repository
import com.medicare.ui.theme.Teal10
import com.medicare.ui.theme.Teal90

@Composable
fun ApprovalPage(){
    val approveList = Repository.approveList.observeAsState().value
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

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
            if(approveList.isNullOrEmpty()){
                Text("No pending appointments", color = Teal90, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            else{
                Text("Check pending appointments", color = Teal90, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                LazyColumn {
                    items(approveList.size){index ->
                        SingleApproval(screenWidth,approveList[index],status={ status ->
                            Repository.updateStatus(status,approveList[index])
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun SingleApproval(screenWidth: Dp, approval: Approval, status: (Boolean) -> Unit = {}){

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
            Row(verticalAlignment = Alignment.CenterVertically){
                Button(onClick = { status(true) }, colors = ButtonDefaults.buttonColors(contentColor =Color.Black, containerColor = Color(0xFF4caf50))) {Text("Accept",fontSize = 14.sp)}
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { status(false) },colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color(0xFFf44336))) {Text("Decline",fontSize = 14.sp)}
            }
        }
    }
}