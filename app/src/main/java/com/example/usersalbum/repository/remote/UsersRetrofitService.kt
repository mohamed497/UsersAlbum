package com.example.usersalbum.repository.remote

import com.example.usersalbum.base.Constants
import com.example.usersalbum.models.Album
import com.example.usersalbum.models.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface UsersRetrofitService{
    @GET("users")
    fun getUpdatedTopUsers(): Observable<List<User>>

//    @GET()
//    fun getUserById(id: Int): Observable<User>
    @GET("albums")
    fun getAlbums(@Query("userId") userId: Int): Observable<List<Album>>
}
object UsersService {
    val retrofitService: UsersRetrofitService by lazy {
        retrofit.create(UsersRetrofitService::class.java)
    }
}