package com.tugasbesar.pemesananmakanan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugasbesar.pemesananmakanan.data.api.ApiClient
import com.tugasbesar.pemesananmakanan.data.api.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Represents the UI state of the Login Screen
data class LoginUiState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val loginError: String? = null, // General error for login failure
    val isLoginSuccess: Boolean = false // State to trigger navigation
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun setEmail(email: String) {
        _uiState.value = _uiState.value.copy(emailInput = email, emailError = null, loginError = null)
    }

    fun setPassword(password: String) {
        _uiState.value = _uiState.value.copy(passwordInput = password, passwordError = null, loginError = null)
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        val currentEmail = _uiState.value.emailInput
        val currentPassword = _uiState.value.passwordInput

        // Reset errors
        _uiState.value = _uiState.value.copy(emailError = null, passwordError = null, loginError = null)

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
        } else if (currentPassword.length < 6) { // Example: minimum 6 characters
            _uiState.value = _uiState.value.copy(passwordError = "Password minimal 6 karakter")
            isValid = false
        }
        return isValid
    }

    fun login() {
        if (!validateInputs()) return

        _uiState.value = _uiState.value.copy(isLoading = true, loginError = null)

        viewModelScope.launch {
            try {
                val request = LoginRequest(
                    email = _uiState.value.emailInput,
                    password = _uiState.value.passwordInput
                )

                val response = withContext(Dispatchers.IO) {
                    ApiClient.apiService.login(request)
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccess = true
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loginError = "Gagal login: ${e.message}"
                )
            }
            }
    }

    // Call this after successful navigation to reset the flag
    fun loginHandled() {
        _uiState.value = _uiState.value.copy(isLoginSuccess = false)
    }
}