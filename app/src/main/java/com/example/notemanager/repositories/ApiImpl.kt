package com.example.notemanager.repositories

import com.example.notemanager.model.NoteItem
import kotlinx.coroutines.delay

class ApiImpl: Api {
    var notes = ArrayList<NoteItem>()

    override suspend fun login(username: String, password: String): Boolean {
        delay(1000)
        if (username != "1" || password != "1") {
            throw Exception("Wrong credentials")
        }
        return true
    }

    override suspend fun loadNotes(): List<NoteItem> {
        delay(1000)
        return notes
    }

    override suspend fun addNote(title: String, content: String) {
        delay(1000)
        notes.add(NoteItem(System.currentTimeMillis(), title, content))
    }

    override suspend fun editNote(dt: Long, title: String, content: String) {
        delay(1000)
        for (i in notes.indices) {
            if (notes[i].dateTime == dt) {
                notes[i] = NoteItem(dt, title, content)
                break
            }
        }
    }

    override suspend fun deleteNode(dt: Long) {
        delay(1000)
        for (i in notes.indices) {
            if (notes[i].dateTime == dt) {
                notes.removeAt(i)
                break
            }
        }
    }
}