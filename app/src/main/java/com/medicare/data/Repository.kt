package com.medicare.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

object Repository {

    val doctorProfile = MutableLiveData<Profile?>(Profile())
    val profileList = MutableLiveData<List<Profile>>(emptyList<Profile>())
    val approveList = MutableLiveData<List<Approval>>(emptyList<Approval>())
    val doctorAppointment = MutableLiveData<List<Approval>>(emptyList<Approval>())
    val patientAppointment = MutableLiveData<List<Appointment>>(emptyList<Appointment>())

    // doctor
    fun getDoctorProfile(){
        FirebaseInstance.getDoctorProfile(callback = { dP ->
            doctorProfile.value = dP
        })
    }

    //doctor
    fun saveProfile(profile: Profile, message: (Boolean,String) -> Unit) {
        FirebaseInstance.saveProfile(profile, message = { bool, msg ->
            message(bool,msg)
        })
    }

    //patient
    fun retrieveProfile(){
        FirebaseInstance.retrieveProfile(message = { bool,data ->
            if(bool){
                profileList.value = data
            }
        })
    }

    //patient
    fun requestAppointment(profile: Profile,slot: String,time: String,message: (Boolean,String) -> Unit) {
        FirebaseInstance.requestAppointment(profile,slot,time,message = { bool, msg ->
            message(bool,msg)
        })
    }

    //doctor
    fun getApproval() {
        FirebaseInstance.getApproval(message = { bool, data ->
            if(bool){
                approveList.value = data
            }
        })
    }

    //doctor
    fun updateStatus(status: Boolean,approveAppointment: Approval){
        FirebaseInstance.updateStatus(status,approveAppointment)
    }

    //patient
    fun getPatientAppointment(){
        FirebaseInstance.getPatientAppointment(message = { bool, data ->
            if(bool){
                patientAppointment.value = data
            }
        })
    }

    //doctor
    fun getDoctorAppointment(){
        FirebaseInstance.getDoctorAppointment(message = { bool, data ->
            if(bool){
                doctorAppointment.value = data
            }
        })
    }

    //doctor
    fun resetSlot(message: (String) -> Unit){
        FirebaseInstance.resetSlot(message = { msg ->
            message(msg)
        })
    }

}