package com.tugasbesar.pemesananmakanan.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.tugasbesar.pemesananmakanan.data.model.Menu

@HiltViewModel
class PesananViewModel @Inject constructor() : ViewModel() {
    private val _daftarPesanan = mutableListOf<Menu>()

    fun tambahPesanan(menu: Menu) {
        _daftarPesanan.add(menu)
    }

    fun getPesanan(): List<Menu> = _daftarPesanan

    fun clearPesanan() {
        _daftarPesanan.clear()
    }
}

