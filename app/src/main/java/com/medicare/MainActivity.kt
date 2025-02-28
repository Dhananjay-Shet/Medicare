package com.medicare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.medicare.data.FirebaseInstance
import com.medicare.data.SharedPreferences
import com.medicare.screen.ApprovalPage
import com.medicare.screen.BookingPage
import com.medicare.screen.Doctor_Appointment
import com.medicare.screen.Doctor_HomePage
import com.medicare.screen.Doctor_ProfilePage
import com.medicare.screen.GuidelinesPage
import com.medicare.screen.LoginPage
import com.medicare.screen.Patient_Appointment
import com.medicare.screen.Patient_HomePage
import com.medicare.screen.Patient_ProfilePage
import com.medicare.screen.SplashScreen
import com.medicare.screen.WellnessPage
import com.medicare.ui.theme.MedicareTheme
import com.medicare.ui.theme.Teal90
import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Login

@Serializable
object PatientHome

@Serializable
object DoctorHome

@Serializable
object PatientProfile

@Serializable
object DoctorProfile

@Serializable
object PatientAppointment

@Serializable
object DoctorAppointment

@Serializable
object Guidelines

@Serializable
object Wellness

@Serializable
object Book

@Serializable
object Approve

class MainActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            MedicareTheme {
                val isUserNull = FirebaseInstance.isCurrentUserNull()
                val isUserDoctor = SharedPreferences.isUserDoctor(this.applicationContext)

                val start = if(isUserNull)
                    Login
                else
                    if(isUserDoctor)
                        DoctorHome
                    else
                        PatientHome

                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(Teal90)

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Splash) {
                    composable<Splash> {
                        SplashScreen(nextPage = { navController.navigate(start){
                            popUpTo(Splash) {
                                inclusive = true
                            }
                        } })
                    }

                    composable<Login> {
                        LoginPage(homePage = { isDoctor ->
                            if(isDoctor){ navController.navigate(DoctorHome){popUpTo(Login) { inclusive = true }} }
                            else{ navController.navigate(PatientHome){popUpTo(Login) { inclusive = true }} }
                        })
                    }

                    composable<DoctorHome> {
                        Doctor_HomePage(logout = { navController.navigate(Login){ popUpTo(DoctorHome) { inclusive = true }} },
                            profile = { navController.navigate(DoctorProfile)},
                            approve = { navController.navigate(Approve) },
                            appointment = { navController.navigate(DoctorAppointment) },
                            guidelines = { navController.navigate(Guidelines) })
                    }

                    composable<PatientHome> {
                        Patient_HomePage( logout = { navController.navigate(Login){ popUpTo(PatientHome) { inclusive = true }} },
                            profile = { navController.navigate(PatientProfile)},
                            book = { navController.navigate(Book) },
                            appointment = { navController.navigate(PatientAppointment) },
                            wellness = { navController.navigate(Wellness) },
                        )
                    }

                    composable<PatientProfile> {
                        Patient_ProfilePage()
                    }

                    composable<DoctorProfile> {
                        Doctor_ProfilePage()
                    }

                    composable<PatientAppointment> {
                        Patient_Appointment()
                    }

                    composable<DoctorAppointment> {
                        Doctor_Appointment()
                    }

                    composable<Guidelines> {
                        GuidelinesPage()
                    }

                    composable<Wellness> {
                        WellnessPage()
                    }

                    composable<Book> {
                        BookingPage()
                    }

                    composable<Approve> {
                        ApprovalPage()
                    }
                    
                }
            }
        }
    }
}

