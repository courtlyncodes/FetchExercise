package com.example.fetchexercise.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fetchexercise.Application
import com.example.fetchexercise.data.ListRepository
import com.example.fetchexercise.model.ListItem
import kotlinx.coroutines.launch
import okio.IOException

class ListViewModel(private val listRepository: ListRepository) : ViewModel() {
    var uiState: ListUiState by mutableStateOf(ListUiState.Loading)
        private set

    private var _allListItems = emptyList<ListItem>()

    init {
        getListInfo()
    }

    private fun getListInfo() {
        viewModelScope.launch {
            uiState = try {
                _allListItems = listRepository.getListInfo()
                // Group the items by listId and sort them by name after extracting the item number from the name
                val groupedList =
                    _allListItems.sortedWith(compareBy<ListItem> { it.listId }.thenBy {
                        extractItemNumber(it.name!!)
                    })
                ListUiState.Success(groupedList)
            } catch (e: IOException) {
                ListUiState.Error
            }
        }
    }

    // Helper function to extract the item number from the name to sort numerically instead of lexicographically
    private fun extractItemNumber(name: String): Int? {
        val itemNumber = name.replace(Regex("[^0-9]"), "").toIntOrNull()
        return itemNumber
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                val listRepository = application.container.repository
                ListViewModel(listRepository = listRepository)
            }
        }
    }
}