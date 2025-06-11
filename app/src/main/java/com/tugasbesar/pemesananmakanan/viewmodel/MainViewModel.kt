package com.tugasbesar.pemesananmakanan.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugasbesar.pemesananmakanan.data.api.ApiService
import com.tugasbesar.pemesananmakanan.data.model.Menu
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _menus = mutableStateOf<List<Menu>>(emptyList())
    val menus: State<List<Menu>> = _menus

    fun fetchMenus() {
        viewModelScope.launch {
            try {
                val result = apiService.getMenus() // langsung List<Menu>
                _menus.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching menus", e)
            }
        }
    }
}

