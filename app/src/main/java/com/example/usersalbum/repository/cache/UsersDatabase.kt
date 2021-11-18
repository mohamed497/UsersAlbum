package com.example.usersalbum.repository.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.usersalbum.base.Constants
import com.example.usersalbum.models.Album

@Database(entities = [Album::class], version = Constants.DB_VERSION, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract val userDao: UsersDao

    companion object {

        @Volatile
        private var INSTANCE: UsersDatabase? = null
        fun getInstance(context: Context): UsersDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java, "news_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}