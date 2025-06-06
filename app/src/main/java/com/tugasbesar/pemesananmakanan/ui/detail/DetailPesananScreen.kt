package com.tugasbesar.pemesananmakanan.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasbesar.pemesananmakanan.data.DetailPesanan

@Composable
fun DetailPesananScreen(
    daftarDetailPesanan: List<DetailPesanan>,
    onBackToPesanan: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Detail Pesanan",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (daftarDetailPesanan.isEmpty()) {
            Text(text = "Belum ada detail pesanan.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(daftarDetailPesanan) { detail ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("ID Detail: ${detail.detailId}")
                            Text("ID Pesanan: ${detail.pesananId}")
                            Text("Menu ID: ${detail.menuId}")
                            Text("Jumlah: ${detail.jumlah}")
                            Text("Subtotal: Rp ${detail.subtotal}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackToPesanan, modifier = Modifier.fillMaxWidth()) {
            Text("Kembali ke Pesanan")
        }
    }
}