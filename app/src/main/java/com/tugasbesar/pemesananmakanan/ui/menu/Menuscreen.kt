package com.tugasbesar.pemesananmakanan.ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugasbesar.pemesananmakanan.viewmodel.MenuViewModel

@Composable
fun MenuScreen(viewModel: MenuViewModel = viewModel(), onNavigateToDetail: () -> Unit) {
    val menus by viewModel.menus.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMenus()
    }

    when {
        isLoading -> {
            CircularProgressIndicator()
        }
        error != null -> {
            Text("Terjadi kesalahan: $error")
        }
        else -> {
            LazyColumn {
                items(menus) { menu ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(menu.name, style = MaterialTheme.typography.titleMedium)
                        Text("Rp${menu.price}")
                        Text(menu.description)
                        // Jika ingin menampilkan gambar
                        // AsyncImage(model = menu.image_url, contentDescription = null)
                    }
                }
            }
            }
            }
}

