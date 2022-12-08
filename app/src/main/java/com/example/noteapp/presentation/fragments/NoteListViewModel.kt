package com.example.noteapp.presentation.fragments

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.noteapp.core.BaseViewModel
import com.example.noteapp.core.Resource
import com.example.noteapp.core.UIState
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.usecase.DeleteNoteUseCase
import com.example.noteapp.domain.usecase.EditNoteUseCase
import com.example.noteapp.domain.usecase.GeAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GeAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : BaseViewModel() {

    private val _deleteNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val deleteNoteState = _deleteNoteState.asStateFlow()

    private val _getAllNotesState = MutableStateFlow<UIState<List<Note>>>(UIState.Empty())
    val getAllNotesState = _getAllNotesState.asStateFlow()


    fun deleteNote(note: Note) {
        Log.d("----", "deleteNote")
        deleteNoteUseCase.deleteNote(note).collectData(_deleteNoteState)
    }



    fun getAllNotes() {
        getAllNotesUseCase.getAllNotes().collectData(_getAllNotesState)
//        viewModelScope.launch(Dispatchers.IO) {
//            getAllNotesUseCase.getAllNotes().collect { resource ->
//                when(resource) {
//                    is Resource.Loading -> {
//                        _getAllNotesState.value = UIState.Loading()
//                    }
//                    is Resource.Error -> {
//                        _getAllNotesState.value = UIState.Error(resource.message!!)
//                    }
//                    is  Resource.Success -> {
//                        if (resource.data != null)
//                        _getAllNotesState.value = UIState.Success(resource.data)
//                    }
//                }
//            }
//        }
    }
}