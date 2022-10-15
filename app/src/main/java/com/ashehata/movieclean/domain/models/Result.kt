package com.ashehata.movieclean.domain.models

import androidx.annotation.Keep

@Keep
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val errorMessage: String) : Result<T>()
}
