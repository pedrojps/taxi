package com.example.car_request2.network.remoteDataSourse

import com.example.car_request2.network.ServiceGenerator.createService
import okhttp3.HttpUrl

abstract class BaseRemoteDataSourse(){

    val DEFAULT_PROTOCOL = "https"

    private var address = "xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws."

    open fun <S> getService(
        service: Class<S>, baseUrl: String
    ): S {
        return getService(service, DEFAULT_PROTOCOL, baseUrl)
    }

    @Throws(IllegalArgumentException::class)
    open fun <S> getService(
        service: Class<S>, protocol: String, baseUrl: String
    ): S {
        return try {
            val url = HttpUrl.Builder()
                .scheme("https")
                .host(baseUrl)
                .scheme(protocol)
                .build()
            createService(service, url)
        } catch (e: Exception) {
            throw IllegalArgumentException("O endereço do servidor é inválido.")
        }
    }

    open fun <S> getMainService(service: Class<S>): S {
        return getService(service, address)
    }

}
