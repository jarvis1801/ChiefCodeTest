package com.jarvis.chiefcodetest.data.transaction

import com.jarvis.chiefcodetest.core.util.DateUtil.parseISODate
import com.jarvis.chiefcodetest.data.BaseRepo
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class TransactionRepo @Inject constructor(
    private val remoteDataSource: TransactionRemoteDataSource
) : BaseRepo() {

    companion object {
        const val TRANSACTION_ITEM_COUNT_PER_PAGE = 5
    }

    fun getTransactionListByPage(page: Int): Flow<List<TransactionResponse>> =
        remoteDataSource.transactionList
            .mapNotNull { list ->
                list?.sortedByDescending { it.transactionDate.parseISODate() }
            }
            .map { list ->
                list.filterIndexed { index, it ->
                    val startIndex = (page - 1) * TRANSACTION_ITEM_COUNT_PER_PAGE
                    val endIndex = (page * TRANSACTION_ITEM_COUNT_PER_PAGE)
                    index in startIndex until endIndex
                }
            }
}