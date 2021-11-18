package com.example.usersalbum.repository.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.usersalbum.models.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlbums(user: List<Album>): Completable

    @Query("SELECT * from albums_table ")
    fun getSavedAlbums(): Observable<List<Album>>

}