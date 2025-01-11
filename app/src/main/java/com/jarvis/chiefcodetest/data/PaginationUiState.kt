package com.jarvis.chiefcodetest.data

sealed class PaginationUiState<out T> {
    object InitialLoading : PaginationUiState<Nothing>()
    data class Success<T>(val data: List<T>, val page: Int) : PaginationUiState<T>()
    data class Finish<T>(val data: List<T>, val page: Int) : PaginationUiState<T>()
    data class Error<T>(val currentData: List<T>, val page: Int, val message: String) : PaginationUiState<T>()
    data class LoadingPagination<T>(val currentData: List<T>, val page: Int) : PaginationUiState<T>()
}