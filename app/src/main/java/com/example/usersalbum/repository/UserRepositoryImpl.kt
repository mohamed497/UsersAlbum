package com.example.usersalbum.repository

import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import com.example.usersalbum.repository.cache.UsersCacheRepository
import com.example.usersalbum.repository.cache.UsersDao
import com.example.usersalbum.repository.remote.UsersRemoteRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class UserRepositoryImpl(private val usersDao: UsersDao ) : UserRepository {

    private val cacheRepo: UserRepository = UsersCacheRepository(usersDao)
    private val remoteRepo: UserRepository = UsersRemoteRepository()


    override fun getUsers(): Observable<List<User>> {
        return remoteRepo.getUsers()
            .flatMap { users ->
//                saveUsers(users)
                return@flatMap Observable.just(users)
            }
    }

    override fun getAlbums(userId: Int): Observable<List<Album>> {
        return remoteRepo.getAlbums(userId)
    }

    override fun saveAlbums(albums: List<Album>): Completable {
        return cacheRepo.saveAlbums(albums)
    }


//    override fun saveUser(user: User): Completable {
//        return cacheRepo.saveUser(user)
//    }

    //    override fun saveUsers(users: List<User>): Completable {
//        return cacheRepo.saveUsers(users)
//    }
//
    override fun getSavedAlbums(): Observable<List<Album>> {
        return cacheRepo.getSavedAlbums()
    }
}