package com.android.pokemon_fun.network.core

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume

suspend fun <T : Any> Deferred<Response<T>>.awaitResult(scope: CoroutineScope): Result<T> {
    return suspendCancellableCoroutine { cancellableContinuation ->
        scope.launch {
            try {
                val response = await()
                cancellableContinuation.resume(
                    when {
                        response.isSuccessful -> {
                            val body = response.body()
                            Result.Success (
                                body,
                                response.raw()
                            )
                        }
                        !response.isSuccessful -> {
                            val errorBody = response.raw()
                            Result.Error(errorBody, HttpException(response))
                        }
                        else -> {
                            Result.Exception(HttpException(response))
                        }
                    }
                )
            } catch (e: Throwable) {
                cancellableContinuation.resume(
                    Result.Exception(
                        e
                    )
                )
            }
        }
        registerOnCompletion(cancellableContinuation)
    }
}

private fun Deferred<Response<*>>.registerOnCompletion(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        if (continuation.isCancelled) {
            try {
                cancel()
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
        }
    }

}