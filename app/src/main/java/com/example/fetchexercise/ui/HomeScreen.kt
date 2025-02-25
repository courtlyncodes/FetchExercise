package com.example.fetchexercise.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchexercise.R
import com.example.fetchexercise.model.ListItem

@Composable
fun FetchExerciseApp(modifier: Modifier = Modifier) {
    val viewModel: ListViewModel = viewModel(factory = ListViewModel.Factory)

    ListItemScreen(uiState = viewModel.uiState)
}

@Composable
fun ListItemScreen(uiState: ListUiState, modifier: Modifier = Modifier) {
    when (uiState) {
        is ListUiState.Loading -> LoadingScreen()
        is ListUiState.Success -> ListItemList(uiState.listItems)
        is ListUiState.Error -> ErrorScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemList(listItems: List<ListItem>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.list_items))
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = modifier.padding(padding)
        ) {
            items(listItems.size) {
                listItems[it].name?.let { item ->
                    ListItemCard(
                        listItems[it].listId,
                        item
                    )
                }
            }
        }
    }
}

@Composable
fun ListItemCard(listId: Int, name: String, modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "$listId: ", style = MaterialTheme.typography.titleLarge, color =
                when (listId) {
                    1 -> Color.Red
                    2 -> Color.Magenta
                    3 -> Color.Blue
                    else -> Color.DarkGray
                }
            )
            Text(name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.loading),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray
        )
    }
}

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}