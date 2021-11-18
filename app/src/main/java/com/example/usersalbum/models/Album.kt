package com.example.usersalbum.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "albums_table")
class Album(
    val userId: Int,
    @PrimaryKey
    val id: Int,
    val title: String
)