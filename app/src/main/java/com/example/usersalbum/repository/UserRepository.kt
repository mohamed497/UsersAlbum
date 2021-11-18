package com.example.usersalbum.repository

import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    fun getUsers(): Observable<List<User>>
    fun getAlbums(userId: Int): Observable<List<Album>>
    fun saveAlbums(albums: List<Album>): Completable
    fun getSavedAlbums(): Observable<List<Album>>

}