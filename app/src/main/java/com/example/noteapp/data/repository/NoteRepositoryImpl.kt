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

    //We can do with return type
    override fun addNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.addNote(note.toNoteEntity())
    }

    //Also we can do without return type
    override fun deleteNote(note: Note) = doRequest {
        noteDao.deleteNote(note.toNoteEntity())
    }

    override fun editNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.editNote(note.toNoteEntity())
    }

    override fun getAllNotes(): Flow<Resource<List<Note>>> = flow {
        emit(Resource.Loading(data = null))
        try {
            val data = noteDao.gAllNotes().map { it.toNote() }
            emit(Resource.Success(data))
        } catch (ioException: IOException) {
            emit(Resource.Error(ioException.localizedMessage ?: "Unknown error"))
        }
    }
}