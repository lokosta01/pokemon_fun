package com.android.pokemon_fun.network.core

import okhttp3.Response
import retrofit2.HttpException

/**
 * Класс для обработки ответ awaitResult
 * Содержит в себе данные отета или exception, возникщий в результате выполнения запроса
 */
sealed class Result<out T : Any> {

    /**
     * Класс успешного ответа
     * @param value - модель ответа запроса
     * @param response - полученный ответ
     */
    class Success<out T : Any> (
        val value: T?,
        override val response: Response
    ) : Result<T>(),
        ResponseResultInterface

    /**
     * Класс ошибки сетевого запроса (не обрарабатывает ошибки, пришедшие в поле meta)
     * @param response - полученный ответ
     * @param exception - [HttpException] исключение соответсвующий сетевой ошибки
     */
    class Error(
        override val response: Response,
        override val exception: Throwable
    ) : Result<Nothing>(),
        ResponseResultInterface,
        ErrorResultInterface

    /**
     * Класс исключения при работе запроса
     * @exception - исключение, возникшее в результате выполнения запроса (Timeout, UnknownHostException)
     */
    class Exception(
        override val exception: Throwable
    ) : Result<Nothing>(),
        ErrorResultInterface

}


interface ResponseResultInterface {
    val response: Response
}

interface ErrorResultInterface {
    val exception: Throwable
}

/**
 * Метод для обработки полученного ответа от сетевого запроса
 * Если запрос выполнился успешно, то вернет тип данных, возвращенный сетевым запросом
 * Если запрос ответил ошибкой, то вернет [Throwable], соответсвующий ошибке
 * Если в процессе выполнения запроса, "вылетело" исключение, то вернет [Throwable], соответсвующий исключению
 * @return - ожидаемый тип данных для сетевого запроса (возвращаемы тип данных запроса) или исключение
 */
fun <T : Any> Result<T>.getOrThrow(): T? {
    return when(this) {
        is Result.Success -> value
        is Result.Error -> throw exception
        is Result.Exception -> throw exception
    }
}
