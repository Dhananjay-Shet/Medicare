package com.medicare.data

data class Profile(
    val doctorId: String = "",
    val doctorName: String = "",
    val licenseNo: String ="",
    val specialization: String = "",
    val degree: String = "",
    val consultationFee: String = "Rs.100/-",
    val slot1: Boolean = true,
    val slot2: Boolean = true,
    val slot3: Boolean = true,
)
