package com.example.usersalbum.repository.cache

import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import com.example.usersalbum.repository.UserRepository
import com.example.usersalbum.ui.AlbumsActivity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class UsersCacheRepository(private val usersDao: UsersDao ): UserRepository{
//    private val usersDao: UsersDao = UsersDatabase.getInstance().userDao
    override fun getUsers(): Observable<List<User>> {
        throw UnsupportedOperationException("Cant Get Update Top Users From Cache")
    }

    override fun getAlbums(userId: Int): Observable<List<Album>> {
        throw  UnsupportedOperationException("Cant Get User By Id in Cache")
    }

//    override fun saveUser(user: User): Completable {
//        return usersDao.saveUser(user)
//    }

//    override fun saveUsers(users: List<User>): Completable {
//        return usersDao.saveUsers(users)
//    }
    override fun saveAlbums(albums: List<Album>): Completable {
        return usersDao.saveAlbums(albums)
    }
//
    override fun getSavedAlbums(): Observable<List<Album>> {
        return usersDao.getSavedAlbums()
    }

}