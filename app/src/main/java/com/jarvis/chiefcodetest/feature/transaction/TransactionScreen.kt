package com.jarvis.chiefcodetest.feature.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jarvis.chiefcodetest.data.PaginationUiState
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse
import com.jarvis.chiefcodetest.feature.transaction.preview.TransactionScreenPreviewParameterProvider
import com.jarvis.chiefcodetest.feature.transaction.ui.TransactionListUI
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = hiltViewModel<TransactionViewModel>()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastVisibleIndex ->
                if (lastVisibleIndex == null) return@collectLatest

                val uiStateValue = uiState.value
                val list = when (uiStateValue) {
                    is PaginationUiState.Success -> uiStateValue.data
                    is PaginationUiState.Error -> uiStateValue.currentData
                    is PaginationUiState.Finish -> uiStateValue.data
                    is PaginationUiState.LoadingPagination -> uiStateValue.currentData
                    else -> arrayListOf()
                }
                if (list.size - 1 <= lastVisibleIndex)
                    viewModel.fetchTransactionList()
            }
    }

    Column(modifier = modifier) {

        TransactionListUI(uiState.value, listState)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview(
    @PreviewParameter(provider = TransactionScreenPreviewParameterProvider::class)
    uiState: PaginationUiState<TransactionResponse>,
    modifier: Modifier = Modifier,
) {

    val listState = rememberLazyListState()
    Column(modifier = modifier) {

        TransactionListUI(uiState, listState)
    }
}