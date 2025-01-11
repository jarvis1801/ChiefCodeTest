package com.jarvis.chiefcodetest.core.network

import com.jarvis.chiefcodetest.BuildConfig.X_API_KEY
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject


class KtorClient @Inject constructor() {

    companion object {
        const val HEADER_X_API_KEY = "X-API-KEY"

        val DEFAULT_HEADER_MAP = mapOf<String, String>(HEADER_X_API_KEY to X_API_KEY)
    }

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.ANDROID
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
    }

    suspend inline fun <reified T> getRequest(url: String, headerMap: Map<String, String> = mapOf()): T? {
        return request(
            client
                .get(url) {
                    headers {
                        headerMap.forEach {
                            append(it.key, it.value)
                        }
                        DEFAULT_HEADER_MAP.forEach {
                            append(it.key, it.value)
                        }
                    }
                }
                .bodyAsText()
        )
    }

    suspend inline fun <reified T> postRequest(url: String, headerMap: Map<String, String> = mapOf()): T? {
        return request(
            client
                .post(url) {
                    headers {
                        headerMap.forEach {
                            append(it.key, it.value)
                        }
                        DEFAULT_HEADER_MAP.forEach {
                            append(it.key, it.value)
                        }
                    }
                }
                .bodyAsText()
        )
    }

    inline fun <reified T> request(strResult: String): T? {
//        return try {
            return Json.decodeFromString<T>(strResult)
//        } catch (e: Exception) {
//            println("Error posting data: ${e.message}")
//            null
//        }
    }
}