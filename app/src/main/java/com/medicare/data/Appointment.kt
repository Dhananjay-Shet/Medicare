package com.medicare.data

data class Appointment(
    val profile: Profile = Profile(),
    val date: String = "",
    val time: String = "",
    val status: String = ""
)