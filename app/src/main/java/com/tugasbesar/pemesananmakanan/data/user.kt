package com.tugasbesar.pemesananmakanan.data


@Suppress("SpellCheckingInspection")
data class User(
    val userId: Int,
    val nama: String,
    val email: String,
    val password: String
)
