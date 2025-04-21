package com.medicare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicare.data.Profile
import com.medicare.data.Repository


class DPViewmodel: ViewModel() {

    val doctorProfile = Repository.doctorProfile.value

    val licenseNo = MutableLiveData<String>(doctorProfile?.licenseNo)
    val licenseNoError = MutableLiveData<String>("")
    fun updateLicenseNo(value: String) {
        licenseNo.value = value
        if (value.isBlank()) {
            licenseNoError.value = "License number cannot be empty"
        }
        else if(value.length != 10 ){
            licenseNoError.value = "License number must be 10 digits"
        }
        else {
            licenseNoError.value = ""
        }
    }

    val specialization = MutableLiveData<String>(doctorProfile?.specialization)
    val specializationError = MutableLiveData<String>("")
    fun updateSpecialization(value: String) {
        specialization.value = value
        if (value.isBlank()) {
            specializationError.value = "Specialization cannot be empty"
        }
        else if(value.length < 5 ){
            specializationError.value = "Specialization must be at least 5 characters"
        }
        else if(value.length > 20){
            specializationError.value = "Specialization cannot be more than 20 characters"
        }
        else {
            specializationError.value = ""
        }
    }


    val degree = MutableLiveData<String>(doctorProfile?.degree)
    val degreeError = MutableLiveData<String>("")
    fun updateDegree(value: String) {
        degree.value = value
        if (value.isBlank()) {
            degreeError.value = "Degree cannot be empty"
        }
        else if(value.length < 2 ){
            degreeError.value = "Degree must be at least 2 characters"
        }
        else if(value.length > 20){
            degreeError.value = "Degree cannot be more than 20 characters"
        }
        else {
            degreeError.value = ""
        }
    }

    val consultationFee = MutableLiveData<String>(doctorProfile?.consultationFee)
    fun updateConsultationFee(value: String) {
        consultationFee.value = value
    }

    fun saveProfile(id: String,doctorName: String,message: (Boolean,String) -> Unit) {
        if(!specialization.value.isNullOrBlank() && specializationError.value.isNullOrBlank() && !licenseNo.value.isNullOrBlank() && licenseNoError.value.isNullOrBlank() && !degree.value.isNullOrBlank() && degreeError.value.isNullOrBlank() && !consultationFee.value.isNullOrBlank()){
            val doctorProfile = Profile(id,doctorName,licenseNo.value!!,specialization.value!!,degree.value!!,consultationFee.value!!,
                doctorProfile?.slot1.toString().toBoolean(),doctorProfile?.slot2.toString().toBoolean(),doctorProfile?.slot3.toString().toBoolean())
            Repository.saveProfile(doctorProfile, message = { bool, msg ->
                message(bool,msg)
            })
        }
        else{
            message(false,"Fill all the fields")
        }
    }

    fun resetSlot(message: (String) -> Unit){
        Repository.resetSlot { msg ->
            message(msg)
        }
    }


}