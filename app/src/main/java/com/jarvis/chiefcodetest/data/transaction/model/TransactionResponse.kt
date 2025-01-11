package com.jarvis.chiefcodetest.data.transaction.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val transactionDate: String,
    val description: String,
    val category: String,
    val debit: Double? = null,
    val credit: Double? = null,
    val id: Int,
)
