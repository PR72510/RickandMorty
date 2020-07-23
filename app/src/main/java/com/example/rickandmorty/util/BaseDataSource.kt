package com.example.rickandmorty.util

import android.util.Log
import retrofit2.Response
import java.lang.Exception

/**
 * Created by PR72510 on 23/7/20.
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend() -> Response<T>): Resource<T>{
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T>{
        Log.e("remoteDataSource", message)
        return Resource.Error("Network Call Failed. $message")
    }
}