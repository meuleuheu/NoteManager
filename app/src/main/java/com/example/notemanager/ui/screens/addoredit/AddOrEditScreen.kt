package com.example.notemanager.ui.screens.addoredit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notemanager.MainViewModel
import com.example.notemanager.common.enum.LoadStatus
import com.example.notemanager.ui.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddOrEditScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    addOrEditViewModel: AddOrEditViewModel,
    mainViewModel: MainViewModel,
    index: Int
) {
    // cach khac la dung bien, nhung config change se bi mat
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val state = addOrEditViewModel.uiState.collectAsState()

    if (index >= 0) {
        title = homeViewModel.uiState.value.notes[index].title
        content = homeViewModel.uiState.value.notes[index].content
    }

    LaunchedEffect(state.value.status) {
        if (state.value.status is LoadStatus.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = if (index == -1) "Add" else "Edit") })
        }
    ) {
        Column(
            Modifier.padding(it).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.value.status is LoadStatus.Error) {
                mainViewModel.setError(state.value.status.description)
                addOrEditViewModel.reset()
            }
            OutlinedTextField(
                modifier = Modifier.padding(16.dp),
                value = title,
                onValueChange = { title = it },
                label = { Text(text = "Title" )}
            )
            OutlinedTextField(
                modifier = Modifier.padding(16.dp),
                value = content,
                onValueChange = { content = it },
                label = { Text(text = "Content" )}
            )
            ElevatedButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    if (index == -1) {
                        addOrEditViewModel.addNote(title, content)
                    } else {
                        addOrEditViewModel.editNote(
                            homeViewModel.uiState.value.notes[index].dateTime, title, content
                        )
                    }
                }
            ) {
                Text("Save")
            }
        }
    }
}