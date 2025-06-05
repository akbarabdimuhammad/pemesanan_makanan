package com.tugasbesar.pemesananmakanan.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tugasbesar.pemesananmakanan.R
import com.tugasbesar.pemesananmakanan.data.model.Menu
import com.tugasbesar.pemesananmakanan.viewmodel.PesananViewModel

@Composable
fun MenuScreen(
    onNavigateToDetail: () -> Unit,
    pesananViewModel: PesananViewModel = hiltViewModel()
) {
    val dummyMenus = listOf(
        Menu(menuId = 1, namaMakanan = "Nasi Goreng Spesial", harga = 18000.0, kategori = "Makanan", stok = 10),
        Menu(menuId = 2, namaMakanan = "Ayam Bakar", harga = 22000.0, kategori = "Makanan", stok = 8),
        Menu(menuId = 3, namaMakanan = "Es Teh Manis", harga = 5000.0, kategori = "Minuman", stok = 25),
        Menu(menuId = 4, namaMakanan = "Jus Alpukat", harga = 12000.0, kategori = "Minuman", stok = 15),
        Menu(menuId = 5, namaMakanan = "Mie Goreng", harga = 15000.0, kategori = "Makanan", stok = 12)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Daftar Menu",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(dummyMenus) { menu ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_makanan),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(menu.namaMakanan, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Text("Rp ${menu.harga}", color = Color.Gray)
                            Text("Kategori: ${menu.kategori}", fontSize = 13.sp, color = Color.DarkGray)
                            Text("Stok: ${menu.stok}", fontSize = 13.sp, color = Color.DarkGray)
                        }

                        Button(
                            onClick = {
                                pesananViewModel.tambahPesanan(menu)
                                onNavigateToDetail()
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Pesan")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

