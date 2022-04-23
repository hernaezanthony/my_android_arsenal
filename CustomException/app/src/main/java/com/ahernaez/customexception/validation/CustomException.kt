package com.ahernaez.customexception.validation

import java.lang.Exception

class CustomException {

    class InvalidEmailException(message: String) : Exception(message)
    class InvalidPasswordException(message: String) : Exception(message)
}