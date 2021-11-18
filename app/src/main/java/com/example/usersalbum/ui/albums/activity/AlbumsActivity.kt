package com.example.usersalbum.ui.albums.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.usersalbum.R
import com.example.usersalbum.base.Resource
import com.example.usersalbum.ui.albums.adapter.AlbumsAdapter
import com.example.usersalbum.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_albums.*

class AlbumsActivity : AppCompatActivity() {

    private lateinit var viewModel: UsersViewModel
    private lateinit var albumAdapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        initViewModel()
        getAlbumData()
        observeOnAlbums()
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this)
                .get(UsersViewModel::class.java)
    }

    private fun getAlbumData() {
        viewModel.getAlbumsUsingZip()
        viewModel.getAlbumsFromCache()
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
                    Log.d(AlbumsActivity::class.simpleName, albumsResource.t.toString())
                }
            }
        })
    }

}