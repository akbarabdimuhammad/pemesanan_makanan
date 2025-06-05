package com.tugasbesar.pemesananmakanan.data.model

@Suppress("SpellCheckingInspection")
data class Menu(
    val menuId: Int,
    val namaMakanan: String,
    val harga: Double,
    val kategori: String,
    val stok: Int
)
