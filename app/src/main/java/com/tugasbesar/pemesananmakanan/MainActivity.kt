@file:OptIn(ExperimentalMaterial3Api::class)

package com.tugasbesar.pemesananmakanan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.tugasbesar.pemesananmakanan.data.Pesanan
import com.tugasbesar.pemesananmakanan.ui.auth.LoginScreen
import com.tugasbesar.pemesananmakanan.ui.auth.RegisterScreen
import com.tugasbesar.pemesananmakanan.ui.detail.DetailPesananScreen
import com.tugasbesar.pemesananmakanan.ui.menu.MenuScreen
import com.tugasbesar.pemesananmakanan.ui.pesanan.PesananScreen
import com.tugasbesar.pemesananmakanan.ui.theme.PemesananMakananTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PemesananMakananTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Aplikasi Pemesanan Makanan") })
        }
    ) { paddingValues ->
        NavGraph(navController = navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("menu") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen<Any>(
                onRegisterSuccess = { navController.navigate("login") },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("menu") {
            MenuScreen(
                onNavigateToDetail = { navController.navigate("pesanan") }  // navigasi ke PesananScreen
            )
        }
        composable("pesanan") {
            val dummyPesanan = listOf(
                Pesanan(1, 1, java.util.Date(), 36000.0, "Diproses"),
                Pesanan(2, 1, java.util.Date(), 22000.0, "Selesai")
            )
            PesananScreen(
                daftarPesanan = dummyPesanan,
                onNavigateToDetail = { navController.navigate("detail") },
                onBackToMenu = { navController.navigate("menu") }  // <-- disini
            )
        }
        composable("detail") {
            // Dummy data detail pesanan
            val dummyDetailPesanan = listOf(
                // Contoh: idDetail, idPesanan, idMenu, qty, hargaTotal
                com.tugasbesar.pemesananmakanan.data.DetailPesanan(1, 1, 1, 2, 36000.0),
                com.tugasbesar.pemesananmakanan.data.DetailPesanan(2, 1, 2, 1, 22000.0)
            )
            DetailPesananScreen(
                daftarDetailPesanan = dummyDetailPesanan,
                onBackToPesanan = { navController.navigate("pesanan") }
            )
        }
    }
}

