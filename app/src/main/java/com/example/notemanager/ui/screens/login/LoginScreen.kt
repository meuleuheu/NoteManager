package com.example.notemanager.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.notemanager.MainViewModel
import com.example.notemanager.common.enum.LoadStatus

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel,
    mainViewModel: MainViewModel
) {
    val state = viewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.value.status is LoadStatus.Loading) {
            CircularProgressIndicator()
        } else if (state.value.status is LoadStatus.Success) {
            // navigates to Home screen
        } else {
            if (state.value.status is LoadStatus.Error) {
                mainViewModel.setError(state.value.status.description)
                viewModel.reset()
            }
            // edit text for user name
            OutlinedTextField(
                value = state.value.username,
                onValueChange = {
                    viewModel.updateUserName(it)
                },
                modifier = Modifier.padding(16.dp),
                label = {
                    Text(text = "User name")
                }
            )
            // edit text for password
            OutlinedTextField(
                value = state.value.password,
                onValueChange = {
                    viewModel.updatePassword(it)
                },
                modifier = Modifier.padding(16.dp),
                label = {
                    Text(text = "Password")
                }
            )
            // button Login
            ElevatedButton(
                onClick = { viewModel.login() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        NavHostController(LocalContext.current),
        LoginViewModel(null, null),
        MainViewModel()
    )
}