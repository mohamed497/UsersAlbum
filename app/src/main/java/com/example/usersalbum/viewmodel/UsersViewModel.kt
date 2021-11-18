package com.example.usersalbum.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.usersalbum.base.Constants
import com.example.usersalbum.base.Resource
import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import com.example.usersalbum.repository.UserRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.zip
import io.reactivex.rxjava3.schedulers.Schedulers

class UsersViewModel : ViewModel() {
    private val userRepositoryImpl = UserRepositoryImpl()
    private val compositeDisposable = CompositeDisposable()

    private val albumsMutableLiveData = MutableLiveData<Resource<List<Album>>>()

    fun observeOnAlbums(lifecycle: LifecycleOwner, albums: Observer<Resource<List<Album>>>) {
        albumsMutableLiveData.observe(lifecycle, albums)
    }

    private fun getSingleAlbum(user: User): Observable<List<Album>> {
        return userRepositoryImpl.getAlbums(user.id)
    }


    fun getAlbumsUsingZip() {
        userRepositoryImpl.getUsers()
            .flatMap { users ->
                val album = users.map(::getSingleAlbum)
                return@flatMap Observable.just(album)
            }.flatMap { allAlbums ->
                return@flatMap Observable.create<List<Album>> { emitter ->
                    allAlbums.zip { albumsUser ->
                        emitter.onNext(albumsUser.flatten())

                    }.subscribeOn(Schedulers.io())
                        .subscribe()
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { albumsList ->
                insertAlbums(albumsList)

            },
                onError = { throwable ->
                    Log.d(Constants.VIEW_MODEL_ERROR, throwable.toString())
                },
                onComplete = { Log.d(Constants.VIEW_MODEL_SUCCESS, "Albums_CompleteD") })
            .let { disposable ->
                compositeDisposable.add(disposable)
            }

    }

    fun getAlbumsFromCache() {
        albumsMutableLiveData.value = Resource.loading()
        userRepositoryImpl.getSavedAlbums().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { albumsList ->
                albumsMutableLiveData.value = Resource.success(albumsList)
            },
                onError = { throwable ->
                    albumsMutableLiveData.value = Resource.error(throwable)
                    Log.d(Constants.VIEW_MODEL_ERROR, throwable.toString())
                },
                onComplete = {
                    Log.d(
                        Constants.VIEW_MODEL_SUCCESS,
                        "Fetch Data From DB Completed"
                    )
                }).let { disposable ->
                compositeDisposable.add(disposable)
            }

    }

    private fun insertAlbums(albums: List<Album>) {
        userRepositoryImpl.saveAlbums(albums).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { Log.d(Constants.VIEW_MODEL_SUCCESS, "Albums Inserted") },
                onError = { throwable ->
                    Log.d(Constants.VIEW_MODEL_ERROR, throwable.toString())
                }
            ).let { disposable ->
                compositeDisposable.add(disposable)
            }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
