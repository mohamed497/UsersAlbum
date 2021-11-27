package com.example.usersalbum.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
)