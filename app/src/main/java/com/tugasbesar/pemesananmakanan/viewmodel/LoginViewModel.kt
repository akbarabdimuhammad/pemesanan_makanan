package com.tugasbesar.pemesananmakanan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
        if (!validateInputs()) {
            return // Stop if validation fails
        }

        _uiState.value = _uiState.value.copy(isLoading = true, loginError = null)

        viewModelScope.launch {
            // --- Simulate a network call (replace with actual API call) ---
            kotlinx.coroutines.delay(2000) // Simulate network delay

            val email = _uiState.value.emailInput
            val password = _uiState.value.passwordInput

            if (email == "test@example.com" && password == "password123") {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccess = true, // Set success state
                    loginError = null
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loginError = "Email atau password salah",
                    isLoginSuccess = false
                )
            }
        }
    }

    // Call this after successful navigation to reset the flag
    fun loginHandled() {
        _uiState.value = _uiState.value.copy(isLoginSuccess = false)
    }
}