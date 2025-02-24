package com.example.fetchexercise.ui

import com.example.fetchexercise.model.ListItem

sealed interface ListUiState {
    data class Success(val listItems: List<ListItem>) : ListUiState
    data object Loading : ListUiState
    data object Error : ListUiState
}