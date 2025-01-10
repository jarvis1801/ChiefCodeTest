package com.jarvis.chiefcodetest.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HeadersBuilder
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class KtorClient {

    val client = HttpClient(CIO) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
    }

    suspend inline fun <reified T> getRequest(url: String, crossinline header: HeadersBuilder.() -> Unit = {}): T? {
        return request(
            client
                .get(url) {
                    headers {
                        header()
                    }
                }
                .body()
        )
    }

    suspend inline fun <reified T> postRequest(url: String, crossinline header: HeadersBuilder.() -> Unit = {}): T? {
        return request(
            client
                .post(url) {
                    headers {
                        header()
                    }
                }
                .body()
        )
    }

    suspend inline fun <reified T> request(block: suspend () -> T): T? {
        return try {
            block()
        } catch (e: Exception) {
            println("Error posting data: ${e.message}")
            null
        }
    }
}