package com.example.chua.finalsexam

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*
import java.util.ArrayList

/**
 * Created by Chua on 3/21/2018.
 */
class AlbumAdapter (private val album: ArrayList<Album>): RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val album: Album = album[position]
        holder?.view?.txtAlbum?.text = album.name
        holder?.view?.txtSinger?.text = album.artist
        val albumImage = holder!!.view.imgPic

        if(album.image == "null"){
            Picasso.with(holder.view.context).load(album.image).into(albumImage)
        }else{
            Picasso.with(holder.view.context).load(album.image).into(albumImage)
        }
    }

    override fun getItemCount(): Int {
        return album.size
    }

    class ViewHolder (val view: View): RecyclerView.ViewHolder(view)
}