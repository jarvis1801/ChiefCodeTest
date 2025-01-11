package com.jarvis.chiefcodetest.feature.transaction

import androidx.lifecycle.viewModelScope
import com.jarvis.chiefcodetest.core.base.BaseViewModel
import com.jarvis.chiefcodetest.data.PaginationUiState
import com.jarvis.chiefcodetest.data.transaction.TransactionRepo
import com.jarvis.chiefcodetest.data.transaction.TransactionRepo.Companion.TRANSACTION_ITEM_COUNT_PER_PAGE
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    val repo: TransactionRepo
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<PaginationUiState<TransactionResponse>>(PaginationUiState.InitialLoading)
    val uiState: StateFlow<PaginationUiState<TransactionResponse>> = _uiState.asStateFlow()

    private var _isLoading = false
    private var retryCount = 0

    fun fetchTransactionList() {
        val fetchPage = getFetchPage()

        if (fetchPage == -1 || _isLoading || retryCount >= 3)
            return
        else
            _isLoading = true

        if (fetchPage == 1)
            _uiState.value = PaginationUiState.InitialLoading
        else
            _uiState.value = PaginationUiState.LoadingPagination(getCurrentList(), fetchPage)

        viewModelScope.launch(IO) {
            delay(4000L) // demo for loading
            repo.getTransactionListByPage(fetchPage)
                .catch { e ->
                    retryCount++
                    _uiState.value = PaginationUiState.Error(getCurrentList(), fetchPage, e.message.toString())
                    _isLoading = false
                    fetchTransactionList()
                }
                .collect { fetchedItems ->
                    val currentList = getCurrentList()
                    val newList = currentList.apply {
                        addAll(fetchedItems)

                    }
                    if (fetchedItems.size < TRANSACTION_ITEM_COUNT_PER_PAGE) {
                        _uiState.value = PaginationUiState.Finish(newList, fetchPage)
                    } else {
                        _uiState.value = PaginationUiState.Success(newList, fetchPage)
                    }
                    _isLoading = false
                }
        }
    }

    private fun getCurrentList(): MutableList<TransactionResponse> {
        val uiStateValue = _uiState.value
        return when (uiStateValue) {
            is PaginationUiState.InitialLoading -> arrayListOf()
            is PaginationUiState.Success -> uiStateValue.data
            is PaginationUiState.Error -> uiStateValue.currentData
            is PaginationUiState.LoadingPagination -> uiStateValue.currentData
            is PaginationUiState.Finish -> uiStateValue.data
        }.toMutableList()
    }

    private fun getFetchPage(): Int {
        val uiStateValue = _uiState.value
        return when (uiStateValue) {
            is PaginationUiState.InitialLoading -> 1
            is PaginationUiState.Success -> uiStateValue.page + 1
            is PaginationUiState.Error -> uiStateValue.page
            else -> -1
        }
    }
}