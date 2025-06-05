package com.tugasbesar.pemesananmakanan.data

@Suppress("SpellCheckingInspection")
data class DetailPesanan(
    val detailId: Int,
    val pesananId: Int,
    val menuId: Int,
    val jumlah: Int,
    val subtotal: Double
)