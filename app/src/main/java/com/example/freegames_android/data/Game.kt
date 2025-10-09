package com.example.freegames_android.data

import com.google.gson.annotations.SerializedName

data class Game (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("game_url") val gameUrl: String,
    @SerializedName("short_description") val descriptionShort: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("platform") val platform: String,
    @SerializedName("description") val description: String?
)