package com.example.noteapp.domain.usecase

import com.example.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class GeAllNotesUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    fun getAllNotes() = noteRepository.getAllNotes()
}