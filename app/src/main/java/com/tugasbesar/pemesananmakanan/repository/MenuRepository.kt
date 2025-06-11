package com.tugasbesar.pemesananmakanan.repository

import com.tugasbesar.pemesananmakanan.data.api.ApiClient
import com.tugasbesar.pemesananmakanan.data.model.Menu

class MenuRepository {
    suspend fun fetchMenus(): List<Menu> {
        return ApiClient.apiService.getMenus()
        }
}