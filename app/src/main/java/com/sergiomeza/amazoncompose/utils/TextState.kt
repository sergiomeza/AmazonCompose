package com.sergiomeza.amazoncompose.utils

class TextState :
    TextFieldState(validator = ::isTextValid, errorFor = ::textValidationError)

/**
 * Returns an error to be displayed or null if no error was found
 */
private fun textValidationError(text: String): String {
    return "Campo invalido"
}

private fun isTextValid(text: String): Boolean {
    return text.isNotEmpty() || text.isNotBlank()
}