package com.tugasbesar.pemesananmakanan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugasbesar.pemesananmakanan.data.api.ApiClient
import com.tugasbesar.pemesananmakanan.data.api.request.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class RegisterUiState(
    val nameInput: String = "", // Tambahkan ini
    val emailInput: String = "",
    val passwordInput: String = "",
    val confirmPasswordInput: String = "",
    val nameError: String? = null, // Tambahkan ini
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val registerError: String? = null,
    val isRegisterSuccess: Boolean = false
)

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun setName(name: String) { // Tambahkan ini
        _uiState.value = _uiState.value.copy(nameInput = name, nameError = null, registerError = null)
    }

    fun setEmail(email: String) {
        _uiState.value = _uiState.value.copy(emailInput = email, emailError = null, registerError = null)
    }

    fun setPassword(password: String) {
        _uiState.value = _uiState.value.copy(passwordInput = password, passwordError = null, registerError = null)
    }

    fun setConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPasswordInput = confirmPassword, confirmPasswordError = null, registerError = null)
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        val currentName = _uiState.value.nameInput // Ambil nilai nama
        val currentEmail = _uiState.value.emailInput
        val currentPassword = _uiState.value.passwordInput
        val currentConfirmPassword = _uiState.value.confirmPasswordInput

        // Reset errors
        _uiState.value = _uiState.value.copy(
            nameError = null, // Reset error nama
            emailError = null,
            passwordError = null,
            confirmPasswordError = null,
            registerError = null
        )

        // Validasi Nama
        if (currentName.isBlank()) {
            _uiState.value = _uiState.value.copy(nameError = "Nama tidak boleh kosong")
            isValid = false
        }

        if (currentEmail.isBlank()) {
            _uiState.value = _uiState.value.copy(emailError = "Email tidak boleh kosong")
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
            _uiState.value = _uiState.value.copy(emailError = "Format email tidak valid")
            isValid = false
        }

        if (currentPassword.isBlank()) {
            _uiState.value = _uiState.value.copy(passwordError = "Password tidak boleh kosong")
            isValid = false
        } else if (currentPassword.length < 6) {
            _uiState.value = _uiState.value.copy(passwordError = "Password minimal 6 karakter")
            isValid = false
        }

        if (currentConfirmPassword.isBlank()) {
            _uiState.value = _uiState.value.copy(confirmPasswordError = "Konfirmasi password tidak boleh kosong")
            isValid = false
        } else if (currentPassword != currentConfirmPassword) {
            _uiState.value = _uiState.value.copy(confirmPasswordError = "Password dan konfirmasi password tidak cocok")
            isValid = false
        }
        return isValid
    }

    fun register() {
        if (!validateInputs()) return

        _uiState.value = _uiState.value.copy(isLoading = true, registerError = null)

        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    name = _uiState.value.nameInput,
                    email = _uiState.value.emailInput,
                    password = _uiState.value.passwordInput
                )

                val response = withContext(Dispatchers.IO) {
                    ApiClient.apiService.register(request)
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRegisterSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    registerError = "Gagal mendaftar: ${e.message}"
                )
                }
            }
    }

    fun registrationHandled() {
        _uiState.value = _uiState.value.copy(isRegisterSuccess = false)
    }
}