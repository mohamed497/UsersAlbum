package com.example.usersalbum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.usersalbum.R
import com.example.usersalbum.base.Resource
import com.example.usersalbum.repository.UserRepositoryImpl
import com.example.usersalbum.repository.cache.UsersDao
import com.example.usersalbum.repository.cache.UsersDatabase
import com.example.usersalbum.ui.adapter.AlbumsAdapter
import com.example.usersalbum.viewmodel.UsersViewModel
import com.example.usersalbum.viewmodel.UsersViewModelFactory
import kotlinx.android.synthetic.main.activity_albums.*

class AlbumsActivity : AppCompatActivity() {

    private lateinit var viewModel: UsersViewModel
    private lateinit var dao: UsersDao
    private lateinit var albumAdapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        initDatabaseDao()
        initViewModel()

        viewModel.getAlbumsForAllUsers()
        viewModel.getAlbumsFromCache()
        observeOnAlbums()
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, UsersViewModelFactory(UserRepositoryImpl(dao)))
                .get(UsersViewModel::class.java)
    }

    private fun initDatabaseDao() {
        dao = UsersDatabase.getInstance(application).userDao
    }

    private fun initAdapter() {
        albumAdapter = AlbumsAdapter()
        albumRecyclerView.adapter = albumAdapter
    }

    private fun observeOnAlbums() {
        initAdapter()
        viewModel.observeOnAlbums(this, { albumsResource ->
            when (albumsResource.state) {
                Resource.Companion.State.LOADING -> {
                    progress.visibility = View.VISIBLE
                    errorText.visibility = View.GONE

                }
                Resource.Companion.State.SUCCESS -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.GONE
                    albumAdapter.setAlbums(albumsResource.value!!)
                }
                Resource.Companion.State.ERROR -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                }
            }
        })
    }

}