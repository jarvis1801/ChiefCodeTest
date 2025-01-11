package com.jarvis.chiefcodetest.data.transaction

import com.jarvis.chiefcodetest.data.BaseDataSource
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransactionRemoteDataSource @Inject constructor(
    private val service: TransactionService
) : BaseDataSource() {

    val transactionList: Flow<List<TransactionResponse>?> = flow {
        emit(service.getTransactionList())
    }
}