package com.example.noteapp.data.repository

import com.example.noteapp.core.BaseRepository
import com.example.noteapp.core.Resource
import com.example.noteapp.data.local.NoteDao
import com.example.noteapp.data.mapper.toNote
import com.example.noteapp.data.mapper.toNoteEntity
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
    ) : NoteRepository, BaseRepository() {

    override fun addNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.addNote(note.toNoteEntity())
    }

    override fun deleteNote(note: Note): Flow<Resource<Unit>> = flow {
        Resource.Loading(null)
        try {
            val data = noteDao.deleteNote(note.toNoteEntity())
            Resource.Success(data)
        } catch (ioException: IOException) {
            Resource.Error(ioException.localizedMessage?: "Unknown error")
        }
    }

    override fun editNote(note: Note): Flow<Resource<Note>> = flow {
        Resource.Loading(null)
        try {
            val data = noteDao.editNote(note.toNoteEntity())
            Resource.Success(data)
        } catch (ioException: IOException) {
            Resource.Error(ioException.localizedMessage?: "Unknown error")
        }
    }

    override fun getAllNotes(): Flow<Resource<List<Note>>> = flow {
        Resource.Loading(data = null)
        try {
            val data = noteDao.gAllNotes().map { it.toNote() }
            Resource.Success(data)
        } catch (ioException: IOException) {
            Resource.Error(ioException.localizedMessage?: "Unknown error")
        }
    }
}