package com.example.noteapp.data.mapper

import com.example.noteapp.data.model.NoteEntity
import com.example.noteapp.domain.model.Note

fun Note.toNoteEntity() = NoteEntity(
    id, title, description, createdAt
)

fun NoteEntity.toNote() = Note(
    id, title, description, createdAt
)