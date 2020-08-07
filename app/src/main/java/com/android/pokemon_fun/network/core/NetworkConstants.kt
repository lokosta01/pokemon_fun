package com.android.pokemon_fun.network.core

/**
 * Статусы сетевого запроса
 * @sample SUCCESS - успешный ответ запроса
 * @sample ERROR - ошибка во время выполнения или запрос ответил ошибкой
 * @sample WAIT - запрос выполняется
 */
enum class Status {
    SUCCESS, ERROR, WAIT
}