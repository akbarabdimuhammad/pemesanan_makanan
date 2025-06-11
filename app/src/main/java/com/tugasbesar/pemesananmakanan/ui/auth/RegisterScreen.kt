package com.tugasbesar.pemesananmakanan.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugasbesar.pemesananmakanan.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel() // Dapatkan instance ViewModel
) {
    // Amati UI state dari ViewModel
    val uiState by viewModel.uiState.collectAsState() // Ini yang akan memberikan nilai input

    // Efek samping untuk menangani navigasi setelah pendaftaran berhasil
    LaunchedEffect(uiState.isRegisterSuccess) {
        if (uiState.isRegisterSuccess) {
            onRegisterSuccess() // Navigasi setelah sukses
            viewModel.registrationHandled() // Reset flag
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Daftar Akun Baru", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(32.dp))

        // Field Nama
        OutlinedTextField(
            value = uiState.nameInput, // Ambil nilai dari uiState
            onValueChange = { viewModel.setName(it) }, // Panggil fungsi di ViewModel untuk update
            label = { Text("Nama") },
            isError = uiState.nameError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        uiState.nameError?.let { errorText ->
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Field Email
        OutlinedTextField(
            value = uiState.emailInput, // Ambil nilai dari uiState
            onValueChange = { viewModel.setEmail(it) }, // Panggil fungsi di ViewModel untuk update
            label = { Text("Email") },
            isError = uiState.emailError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        uiState.emailError?.let { errorText ->
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Field Password
        OutlinedTextField(
            value = uiState.passwordInput, // Ambil nilai dari uiState
            onValueChange = { viewModel.setPassword(it) }, // Panggil fungsi di ViewModel untuk update
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = uiState.passwordError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        uiState.passwordError?.let { errorText ->
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Field Konfirmasi Password
        OutlinedTextField(
            value = uiState.confirmPasswordInput, // Ambil nilai dari uiState
            onValueChange = { viewModel.setConfirmPassword(it) }, // Panggil fungsi di ViewModel untuk update
            label = { Text("Konfirmasi Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = uiState.confirmPasswordError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        uiState.confirmPasswordError?.let { errorText ->
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        uiState.registerError?.let { errorText ->
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.register() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Daftar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onNavigateToLogin() }) {
            Text("Sudah punya akun? Login")
        }
    }
}