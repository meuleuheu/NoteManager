package com.example.notemanager.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.notemanager.repositories.Api
import com.example.notemanager.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val log: MainLog?,
    private val api: Api?
) : ViewModel() {
}