package com.tugasbesar.pemesananmakanan.ui.pesanan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasbesar.pemesananmakanan.data.Pesanan
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PesananScreen(
    daftarPesanan: List<Pesanan>,
    onNavigateToDetail: () -> Unit,
    onBackToMenu: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Daftar Pesanan",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (daftarPesanan.isEmpty()) {
            Text("Belum ada pesanan.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(daftarPesanan) { pesanan ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("ID Pesanan: ${pesanan.pesananId}")
                            Text("User ID: ${pesanan.userId}")
                            Text(
                                "Tanggal Pesan: ${
                                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                        pesanan.tanggalPesan
                                    )
                                }"
                            )
                            Text("Total Harga: Rp ${pesanan.totalHarga}")
                            Text("Status: ${pesanan.statusPesanan}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBackToMenu) {
                Text("Kembali ke Menu")
            }

            Button(onClick = onNavigateToDetail) {
                Text("Lihat Detail")
            }
        }
    }
}

