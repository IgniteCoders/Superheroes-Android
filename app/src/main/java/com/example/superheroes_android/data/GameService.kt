package com.example.superheroes_android.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GameService {

    @GET("games")
    suspend fun getAllGames(): List<Game>

    @GET("game")
    suspend fun getGameById(@Query("id") id: String): Game

    companion object {
        fun getInstance(): GameService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.freetogame.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(GameService::class.java)
        }
    }
}