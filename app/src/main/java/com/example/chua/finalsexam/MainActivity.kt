package com.example.chua.finalsexam

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var txtSinger: String? = null
    private var Album = ArrayList<Album>()
    private var RecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = cm.activeNetworkInfo

        RecyclerView = findViewById(R.id.recyclerView)
        RecyclerView!!.layoutManager = LinearLayoutManager(this)

        Search?.setOnEditorActionListener (object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (networkInfo?.type == ConnectivityManager.TYPE_WIFI || networkInfo?.type == ConnectivityManager.TYPE_MOBILE) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        txtSinger = Search.text.toString()
                        progressBar.visibility = View.VISIBLE
                        textView.visibility = View.GONE

                        for (i in 1..50) {
                            doAsync {
                                val resultJson = URL("http://ws.audioscrobbler.com/2.0/?method=album.search&album=$txtSinger&api_key=a04bda2f3f26d70f47ac108d85760d05&format=json").readText()
                                val jsonObject = JSONObject(resultJson)

                                val albumName = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getString("name")
                                val artistName = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getString("artist")
                                var imgLink = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text")

                                if (imgLink == "") {
                                    imgLink = "null"
                                }

                                uiThread {
                                    Album.add(Album(albumName, artistName, imgLink))
                                    RecyclerView!!.adapter = AlbumAdapter(Album)
                                    recyclerView.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                }
                            }
                        }
                        return true
                    }
                }
                else{
                    textView.text = "No internet service"
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
                return false
            }
        })
    }
}