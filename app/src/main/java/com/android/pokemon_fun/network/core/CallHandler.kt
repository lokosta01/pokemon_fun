package com.android.pokemon_fun.network.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response

class CallHandler<T: Any> {

    /**
     * Запрос, ожидающий [Deferred] в качестве возвратных данных функции (метода)
     */
    lateinit var client: Deferred<Response<T>>

    /**
     * Альтернативный механизм вызова сетевых запросов
     * Реализует выполнения запроса в фоновом потоке и передачу результата в главный поток с помощью liveData
     * @param scope - [CoroutineScope] в котором нужно выполнять запрос
     * @return [LiveData] с классом [Resource], содержащий в себе статус запроса и данные ответа
     */
    fun makeCall(scope: CoroutineScope): LiveData<Resource<T>> {
        val result = MutableLiveData<Resource<T>>()
        result.value =
            Resource.wait()
        scope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = client.awaitResult(scope).getOrThrow()
                    withContext(Dispatchers.Main) {
                      result.value = Resource.success(response)
                   }
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                       result.value = Resource.error(e.message(), e.code())
                    }
                }  catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        result.value =
                            Resource.error(
                                e.toString(), -1
                            )
                    }
                }
            }
        }
        return result
    }

    /**
     * Метод для вызова сетевого запроса
     * @param scope [CoroutineScope] в котором нужно выполнять запрос
     */
    fun <T : Any> networkCall(scope: CoroutineScope, block: CallHandler<T>.() -> Unit): LiveData<Resource<T>>
            = CallHandler<T>().apply(block).makeCall(scope)
}