package com.medicare.data

data class Feedback(
    val provider: String = "",
    val recipient: String = "",
    val review: String = "",
    val rating: Int = 0,
    val dateTime: String = "",
)
