package com.hyperaware.bfa.android.util

sealed class Resource<T>(val data: T? = null, val e: Exception? = null)
class Loading<T>(data: T? = null) : Resource<T>(data)
class Success<T>(data: T) : Resource<T>(data)
class Failure<T>(e: Exception, data: T? = null) : Resource<T>(data, e)
