package com.example.notemanager.repositories

import com.example.notemanager.model.NoteItem

interface Api {
    suspend fun login(username: String, password: String): Boolean
    suspend fun loadNotes(): List<NoteItem>
    suspend fun addNote(title: String, content: String)
    suspend fun editNote(id: Long, title: String, content: String)
    suspend fun deleteNode(dt: Long)
}