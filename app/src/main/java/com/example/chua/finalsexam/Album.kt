package com.example.chua.finalsexam

import com.google.gson.annotations.SerializedName

/**
 * Created by Chua on 3/21/2018.
 */
data class Album(
        val name: String,
        val artist: String,
        @SerializedName("#text") val image: String = ""
)