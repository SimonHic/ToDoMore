package ie.setu.todomore.data.rules

import android.util.Patterns

object Validator{
    fun validateFirstName(name: String): ValidationResult {
        return ValidationResult(
            status = name.isNotBlank(),
            //successful = name.isNotBlank(),
            errorMessage = if (name.isBlank()) "Name cannot be empty." else null
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            status = Patterns.EMAIL_ADDRESS.matcher(email).matches(),
            //successful = Patterns.EMAIL_ADDRESS.matcher(email).matches(),
            errorMessage = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email." else null
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            status = password.length >= 6,
            //successful = password.length >= 6,
            errorMessage = if (password.length < 6) "Password must be at least 6 characters." else null
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue:Boolean): ValidationResult {
        return ValidationResult(
            status = statusValue,
            errorMessage = if (!statusValue) "You must accept the privacy policy" else null
        )
    }
}

data class ValidationResult(
    val status: Boolean,
    //val successful: Boolean,
    val errorMessage: String? = null
)