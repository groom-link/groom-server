package com.groom.common

@JvmRecord
data class Response<T>(val success: Boolean, val body: T) {
    constructor(body: T) : this(body is ErrorResponse, body)
}

@JvmRecord
data class ErrorResponse(val status: Int, val message: String) {
    companion object {
        fun of(status: Int, e: Throwable): ErrorResponse {
            return ErrorResponse(status, e.localizedMessage)
        }
    }
}