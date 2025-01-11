package com.jarvis.chiefcodetest.feature.transaction.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarvis.chiefcodetest.core.ui.PaginationLoadingIcon
import com.jarvis.chiefcodetest.data.PaginationUiState
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse

@Composable
fun TransactionListUI(
    uiState: PaginationUiState<TransactionResponse>,
    listState: LazyListState
) {
    val list = when (uiState) {
        is PaginationUiState.Success -> uiState.data
        is PaginationUiState.Error -> uiState.currentData
        is PaginationUiState.Finish -> uiState.data
        is PaginationUiState.LoadingPagination -> uiState.currentData
        else -> arrayListOf()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        state = listState
    ) {
        items(list, key = { it.id }) { transaction ->
            TransactionUIItem(transaction)
        }

        item { PaginationLoadingIcon(uiState) }
    }
}