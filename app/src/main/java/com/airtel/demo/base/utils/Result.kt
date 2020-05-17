package com.airtel.demo.base.utils

sealed class Result<out T: Any>
data class Success<out T: Any>(val data: T): Result<T>()
data class Fail(val StringResId: Int): Result<Nothing>()
