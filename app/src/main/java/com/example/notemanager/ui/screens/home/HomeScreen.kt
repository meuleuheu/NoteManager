package com.example.notemanager.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notemanager.MainViewModel
import com.example.notemanager.Screen
import com.example.notemanager.common.enum.LoadStatus
import com.example.notemanager.ui.screens.detail.DetailNote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
    mainViewModel: MainViewModel
) {
    val state = viewModel.uiState.collectAsState()    // use by -> error

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // hien thi data
    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Note Manager") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddOrEdit.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) {
        Column(Modifier.padding(it)) {
            if (state.value.status is LoadStatus.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                if (state.value.status is LoadStatus.Error) {
                    mainViewModel.setError(state.value.status.description)
                    viewModel.reset()
                }
                if (screenWidth < 600.dp) {
                    ListNote(state, navController, viewModel, false)
                } else {
                    Row {
                        Box(modifier = Modifier.weight(1f)) {
                            ListNote(state, navController, viewModel, true)
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            DetailNote(viewModel, state.value.selectedIndex, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListNote(
    state: State<HomeUiState>,
    navController: NavHostController,
    viewModel: HomeViewModel,
    isSplitMode: Boolean
) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(state.value.notes.size) { idx ->
            ListItem(
                modifier = Modifier.clickable {
                    // navigate to Detail screen
                    if (!isSplitMode) {
                        navController.navigate("${Screen.Detail.route}?noteIndex=${idx}") // theo index
                    } else {
                        viewModel.selectNote(idx)
                    }
                },
                overlineContent = { Text(text = state.value.notes[idx].dateTime.toString()) },
                headlineContent = { Text(text = state.value.notes[idx].title) },
                supportingContent = { Text(text = state.value.notes[idx].content) },
                trailingContent = {
                    IconButton(onClick = { viewModel.deleteNote(state.value.notes[idx].dateTime) }) {
                        Icon(Icons.Filled.Delete, contentDescription = "")
                    }
                }
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = HomeViewModel(null, null),
        mainViewModel = MainViewModel()
    )
}