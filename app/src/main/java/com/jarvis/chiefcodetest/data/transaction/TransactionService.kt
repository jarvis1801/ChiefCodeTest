package com.jarvis.chiefcodetest.data.transaction

import com.jarvis.chiefcodetest.BuildConfig
import com.jarvis.chiefcodetest.core.network.KtorClient
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse
import javax.inject.Inject

class TransactionService @Inject constructor(
    private val client: KtorClient
) {
    companion object {
        private const val TRANSACTION_API_URL = BuildConfig.TRANSACTION_API_URL
    }

    suspend fun getTransactionList(): List<TransactionResponse>? {
        return client.getRequest(TRANSACTION_API_URL)
    }
}