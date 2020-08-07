package com.android.pokemon_fun.network.core

/**
 * Класс ресурс с данными сетевого запроса
 */
open class Resource<T> {

    /**
     * Статус запроса (выпонение, успешный ответ, ошибка)
     */
    var status: Status
    /**
     * Сообщение об ошибке
     */
    var message: String? = null
    /**
     * Модель ответа запроса
     */
    var data: T? = null
    /**
     * Код ошибки
     */
    var errorType: Int? = null

    constructor() {
        status = Status.WAIT
    }

    constructor(status: Status, data: T?) {
        this.status = status
        this.data = data
    }

    constructor(message: String?, errorType: Int?) {
        this.status = Status.ERROR
        this.message = message
        this.errorType = errorType
    }

    companion object {

        fun <T> wait(): Resource<T> = Resource()

        fun <T> success(data: T?): Resource<T> =
            Resource(Status.SUCCESS, data)

        fun <T> error(message: String?, errorType: Int?): Resource<T> =
            Resource(message, errorType)
    }
}