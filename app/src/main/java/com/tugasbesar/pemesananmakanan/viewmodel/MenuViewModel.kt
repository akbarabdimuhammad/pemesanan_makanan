package com.tugasbesar.pemesananmakanan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugasbesar.pemesananmakanan.data.model.Menu
import com.tugasbesar.pemesananmakanan.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    private val repository = MenuRepository()

    private val _menus = MutableStateFlow<List<Menu>>(emptyList())
    val menus: StateFlow<List<Menu>> = _menus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadMenus() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _menus.value = repository.fetchMenus()
            } catch (e: Exception) {
                _error.value = "Gagal memuat menu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
            }
        }
}