package com.jarvis.chiefcodetest.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarvis.chiefcodetest.data.PaginationUiState

@Composable
fun PaginationLoadingIcon(
    uiState: PaginationUiState<Any>,
    modifier: Modifier = Modifier,
) {

    when (uiState) {
        is PaginationUiState.InitialLoading,
        is PaginationUiState.LoadingPagination -> {

            Column(
                modifier = modifier.fillMaxWidth().padding(vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {}
    }
}