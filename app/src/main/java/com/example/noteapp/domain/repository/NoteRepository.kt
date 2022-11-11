package com.example.noteapp.domain.repository

import com.example.noteapp.domain.model.Note


interface NoteRepository {

    fun addNote(note: Note)

    fun deleteNote(note: Note)

    fun editNote(note: Note): Note

    fun getAllNotes(): List<Note>
}