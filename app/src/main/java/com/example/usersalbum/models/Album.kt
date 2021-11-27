package com.example.usersalbum.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "albums_table")
class Album(
    val userId: Int,
    @PrimaryKey
    val id: Int,
    val title: String
)