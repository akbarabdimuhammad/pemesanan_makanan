package com.tugasbesar.pemesananmakanan.data.api

import com.tugasbesar.pemesananmakanan.data.api.request.*
import com.tugasbesar.pemesananmakanan.data.api.response.LoginResponse
import com.tugasbesar.pemesananmakanan.data.api.response.RegisterResponse
import com.tugasbesar.pemesananmakanan.data.api.response.*
import com.tugasbesar.pemesananmakanan.data.model.Menu
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body body: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @GET("menus")
    suspend fun getMenus(): <List<Menu>>
}