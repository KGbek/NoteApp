package com.example.noteapp.data.mapper

import com.example.noteapp.data.model.NoteEntity
import com.example.noteapp.domain.model.Note

fun Note.noteToNoteEntity() = NoteEntity(
    id,
    title,
    description,
    createdAt
)

fun NoteEntity.noteEntityToNote() = Note(
    id,
    title,
    description,
    createdAt
)