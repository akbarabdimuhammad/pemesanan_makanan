package com.tugasbesar.pemesananmakanan.data.api

import com.tugasbesar.pemesananmakanan.data.model.Menu
import com.tugasbesar.pemesananmakanan.data.Pesanan
import com.tugasbesar.pemesananmakanan.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@Suppress("SpellCheckingInspection")
interface ApiService {

    @GET("Menu")
    suspend fun getMenus(): Response<List<Menu>>

    @POST("users/login")
    suspend fun loginUser(@Body user: User): Response<User>

    @POST("pesanan")
    suspend fun createPesanan(@Body pesanan: Pesanan): Response<Pesanan>

    @GET("pesanan/user/{userId}")
    suspend fun getPesananByUser(@Path("userId") userId: String): Response<List<Pesanan>>
}
