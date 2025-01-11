package com.jarvis.chiefcodetest.feature.transaction.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jarvis.chiefcodetest.data.PaginationUiState
import com.jarvis.chiefcodetest.data.transaction.model.TransactionResponse

class TransactionScreenPreviewParameterProvider : PreviewParameterProvider<PaginationUiState<TransactionResponse>> {
    override val values: Sequence<PaginationUiState<TransactionResponse>> = sequenceOf(
        PaginationUiState.Success(data = listOf(
            TransactionResponse(
                "2021-01-05",
                "Company 3",
                "Other Services",
                100.84,
                null,
                1
            ),
            TransactionResponse(
                "2020-01-05",
                "Company 2",
                "Other Services",
                null,
                200.84,
                2
            )
        ), 0),
        PaginationUiState.InitialLoading,
        PaginationUiState.Error(currentData = emptyList(), 0, "") // Empty state
        // You could add more states here, like a loading state if your UI handles it
    )
}