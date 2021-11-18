package com.example.usersalbum.repository.remote

import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import com.example.usersalbum.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UsersRemoteRepository : UserRepository {

    private val userService = UsersService.retrofitService

    override fun getUsers(): Observable<List<User>> {
        return userService.getUpdatedTopUsers()
    }

    override fun getAlbums(userId: Int): Observable<List<Album>> {
        return userService.getAlbums(userId)
    }

    override fun saveAlbums(albums: List<Album>): Completable {
        throw UnsupportedOperationException("You can not save user in Remote layer")
    }

    override fun getSavedAlbums(): Observable<List<Album>> {
        throw UnsupportedOperationException("You can not get saved users in Remote layer")
    }

}