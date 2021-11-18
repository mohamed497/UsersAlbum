package com.example.usersalbum.repository

import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import com.example.usersalbum.repository.cache.UsersCacheRepository
import com.example.usersalbum.repository.cache.UsersDao
import com.example.usersalbum.repository.remote.UsersRemoteRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class UserRepositoryImpl(usersDao: UsersDao) : UserRepository {

    private val cacheRepo: UserRepository = UsersCacheRepository(usersDao)
    private val remoteRepo: UserRepository = UsersRemoteRepository()


    override fun getUsers(): Observable<List<User>> {
        return remoteRepo.getUsers()
            .flatMap { users ->
                return@flatMap Observable.just(users)
            }
    }

    override fun getAlbums(userId: Int): Observable<List<Album>> {
        return remoteRepo.getAlbums(userId)
    }

    override fun saveAlbums(albums: List<Album>): Completable {
        return cacheRepo.saveAlbums(albums)
    }


    override fun getSavedAlbums(): Observable<List<Album>> {
        return cacheRepo.getSavedAlbums()
    }
}