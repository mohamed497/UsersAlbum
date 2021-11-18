package com.example.usersalbum.ui.albums.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.usersalbum.R
import com.example.usersalbum.models.Album
import kotlinx.android.synthetic.main.list_users_item.view.*

class AlbumsAdapter :
    RecyclerView.Adapter<AlbumsAdapter.UserViewHolder>() {

    private var albumsList = ArrayList<Album>()
    fun setAlbums(albumList: List<Album>) {
        this.albumsList.addAll(albumList)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.userId
        val title: TextView = itemView.albumTitle

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_users_item,
            parent, false
        )
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = albumsList[position]
        holder.title.text = currentItem.title
        holder.id.text = currentItem.userId.toString()
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }
}