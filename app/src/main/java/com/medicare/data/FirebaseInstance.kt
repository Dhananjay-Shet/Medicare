package com.medicare.data

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object FirebaseInstance {

    private val auth by lazy { Firebase.auth }
    private var currentUser = auth.currentUser
    private val authUI by lazy { AuthUI.getInstance() }
    private val database by lazy { Firebase.database }
    private val detailsRef by lazy { database.getReference("details") }
    private val doctorRef by lazy { database.getReference("doctor") }
    private val patientRef by lazy { database.getReference("patient") }

    init {
        auth.addAuthStateListener { firebaseAuth ->
            currentUser = firebaseAuth.currentUser
        }
    }

    fun isCurrentUserNull(): Boolean {
        return currentUser == null
    }

    fun getCurrentUser(): FirebaseUser? {
        return currentUser
    }

    fun getSignInIntent(): Intent {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val signInIntent = authUI
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
        return signInIntent
    }

    fun logoutUser(context: Context, onLogoutComplete: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val providerId = currentUser?.providerData[1]?.providerId
            when (providerId) {
                "google.com" -> {
                    authUI.signOut(context).addOnCompleteListener {
                        onLogoutComplete()
                    }
                }
            }
        }
    }

    fun getDoctorProfile(callback: (Profile?) -> Unit){
        detailsRef.child(currentUser?.uid.toString()).addListenerForSingleValueEvent( object : ValueEventListener{
            override fun onDataChange(
                snapshot: DataSnapshot
            ) {
                if(snapshot.exists()){
                    val doctorProfile = snapshot.getValue(Profile::class.java)
                    callback(doctorProfile)
                }
            }

            override fun onCancelled(
                error: DatabaseError
            ) {

            }
        })
    }

    fun saveProfile(profile: Profile, message: (Boolean,String) -> Unit) {
        detailsRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(
                snapshot: DataSnapshot
            ) {
                detailsRef.child(currentUser?.uid.toString()).setValue(profile).addOnSuccessListener {
                    message(true,"Profile saved successfully")
                }.addOnFailureListener {
                    message(false,"Failed to save profile")
                }
            }

            override fun onCancelled(
                error: DatabaseError
            ) {
                message(false,"Error in saving profile")
            }

        })
    }

    fun retrieveProfile(message: (Boolean, List<Profile>) -> Unit){

        detailsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(
                snapshot: DataSnapshot
            ) {
                if(snapshot.exists()){
                    val profile = ArrayList<Profile>()
                    for(eachSnapshot in snapshot.children){
                        val profileData = eachSnapshot.getValue(Profile::class.java)
                        profile.add(profileData!!)
                    }
                    message(true,profile)
                }
                else{
                    message(false, emptyList())
                }
            }

            override fun onCancelled(
                error: DatabaseError
            ) {

            }
        })
    }

    fun requestAppointment(profile: Profile,slot: String,time: String,message: (Boolean,String) -> Unit) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedDate = currentDate.plusDays(1).format(formatter)
        val approve = Approval(currentUser?.uid.toString(),currentUser?.displayName.toString(),formattedDate,slot,time)
        val appointment = Appointment(profile,formattedDate,time,"Pending")
        doctorRef.child(profile.doctorId).child("approval").child(currentUser?.uid.toString()).setValue(approve).addOnSuccessListener {
            patientRef.child(currentUser?.uid.toString()).child("appointment").child("${profile.doctorId}$formattedDate").setValue(appointment)
            message(true,"Appointment request sent successfully")
        }.addOnFailureListener {
            message(false,"Failed to send appointment request")
        }
    }

    fun getApproval(message: (Boolean, List<Approval>) -> Unit) {

        doctorRef.child(currentUser?.uid.toString()).child("approval").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(
                snapshot: DataSnapshot
            ) {
                if(snapshot.exists()){
                    val approve = ArrayList<Approval>(emptyList<Approval>())
                    for(approveSnapshot in snapshot.children){
                        val approveData = approveSnapshot.getValue(Approval::class.java)
                        approve.add(approveData!!)
                    }
                    message(true,approve)
                }
                else{
                    message(false, emptyList())
                }
            }

            override fun onCancelled(
                error: DatabaseError
            ) {

            }

        })
    }

    fun updateStatus(status: Boolean,approveAppointment: Approval){
        doctorRef.child(currentUser?.uid.toString()).child("approval").child(approveAppointment.patientId).removeValue().addOnSuccessListener{
            Repository.approveList.value = Repository.approveList.value?.drop(1)
        }
        if(status){
            detailsRef.child(currentUser?.uid.toString()).child(approveAppointment.slot).setValue(false)
            doctorRef.child(currentUser?.uid.toString()).child("history").push().setValue(approveAppointment)
            patientRef.child(approveAppointment.patientId).child("appointment").child("${currentUser?.uid.toString()}${approveAppointment.date}").child("status").setValue("Accepted")
        }
        else{
            patientRef.child(approveAppointment.patientId).child("appointment").child("${currentUser?.uid.toString()}${approveAppointment.date}").child("status").setValue("Rejected")
        }
    }

    fun getPatientAppointment(message: (Boolean, List<Appointment>) -> Unit){

        patientRef.child(currentUser?.uid.toString()).child("appointment").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(
                snapshot: DataSnapshot
            ) {
                if(snapshot.exists()){
                    val appointment = ArrayList<Appointment>(emptyList<Appointment>())
                    for(appointmentSnapshot in snapshot.children){
                        val appointmentData = appointmentSnapshot.getValue(Appointment::class.java)
                        appointment.add(appointmentData!!)
                    }
                    message(true,appointment)
                }
                else{
                    message(false, emptyList())
                }
            }

            override fun onCancelled(
                error: DatabaseError
            ) {

            }
        })
    }

    fun getDoctorAppointment(message: (Boolean,List<Approval>) -> Unit){

        doctorRef.child(currentUser?.uid.toString()).child("history").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(
                snapshot: DataSnapshot
            ) {
                if(snapshot.exists()){
                    val appointment = ArrayList<Approval>(emptyList<Approval>())
                    for(appointmentSnapshot in snapshot.children){
                        val appointmentData = appointmentSnapshot.getValue(Approval::class.java)
                        appointment.add(appointmentData!!)
                    }
                    message(true,appointment)
                }
                else
                    message(false, emptyList())
            }

            override fun onCancelled(
                error: DatabaseError
            ) {

            }
        })
    }

    fun resetSlot(message: (String) -> Unit){
        val slotsReset = mapOf(
            "slot1" to true,
            "slot2" to true,
            "slot3" to true
        )

        detailsRef.child(currentUser?.uid.toString()).updateChildren(slotsReset)
            .addOnSuccessListener{ message("Slots reset successfully") }
            .addOnFailureListener{ message("Failed to reset slots") }

    }
}