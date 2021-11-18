package com.example.usersalbum.repository.cache

import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import com.example.usersalbum.repository.UserRepository
import com.example.usersalbum.App
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class UsersCacheRepository : UserRepository {
    private val usersDao = UsersDatabase.getInstance(App.instance).userDao
    override fun getUsers(): Observable<List<User>> {
        throw UnsupportedOperationException("Cant Get Update Top Users From Cache")
    }

    override fun getAlbums(userId: Int): Observable<List<Album>> {
        throw  UnsupportedOperationException("Cant Get User By Id in Cache")
    }

    override fun saveAlbums(albums: List<Album>): Completable {
        return usersDao.saveAlbums(albums)
    }

    override fun getSavedAlbums(): Observable<List<Album>> {
        return usersDao.getSavedAlbums()
    }

}