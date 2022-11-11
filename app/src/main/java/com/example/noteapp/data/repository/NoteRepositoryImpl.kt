package com.example.noteapp.data.repository

import com.example.noteapp.data.local.NoteDao
import com.example.noteapp.data.mapper.noteEntityToNote
import com.example.noteapp.data.mapper.noteToNoteEntity
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
    ) : NoteRepository{

    override fun addNote(note: Note) {
        noteDao.addNote(note.noteToNoteEntity())
    }

    override fun deleteNote(note: Note) {
        noteDao.deleteNote(note.noteToNoteEntity())
    }

    override fun editNote(note: Note): Note {
        return noteDao.editNote(note.noteToNoteEntity()).noteEntityToNote()
    }

    override fun getAllNotes(): List<Note> {
        return noteDao.gAllNotes().map { it.noteEntityToNote() }
    }

}