package com.example.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AlbumService {

    @GET(value = "/albums")
    suspend fun getAlbums() : Response<Album>

    @GET(value = "/albums")
    suspend fun getSortedAlbums(@Query("userId") userId:Int) : Response<Album>
}