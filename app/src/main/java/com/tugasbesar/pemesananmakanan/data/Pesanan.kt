package com.tugasbesar.pemesananmakanan.data

import java.util.Date

@Suppress("SpellCheckingInspection")
data class Pesanan(
    val pesananId: Int,
    val userId: Int,
    val tanggalPesan: Date,
    val totalHarga: Double,
    val statusPesanan: String
)
