package com.ahernaez.customexception.validation

class InputValidator {

    companion object {

        fun validateEmail(email: String) {

            if (email.isEmpty()) {
                throw CustomException.InvalidEmailException("Email is required")
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                throw CustomException.InvalidEmailException("Invalid email")
            }
        }

        fun validatePassword(password: String) {


            if (password.isEmpty()){
                throw CustomException.InvalidPasswordException("Password is required")
            }

            if (password.length < 8){
                throw CustomException.InvalidPasswordException("Password must be at least eight characters")
            }

            if (!password.matches(Regex(".*\\d.*"))){
                throw CustomException.InvalidPasswordException("Password must contain at least one number")
            }
        }
    }
}