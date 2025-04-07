package com.example.notemanager.common.enum

sealed class LoadStatus(val description: String = "") {
    class Init(): LoadStatus()
    class Loading(): LoadStatus()
    class Success(): LoadStatus()
    class Error(error: String): LoadStatus(error)
}