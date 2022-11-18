package com.example.noteapp.presentation.fragments.add_note

import androidx.lifecycle.viewModelScope
import com.example.noteapp.core.BaseViewModel
import com.example.noteapp.core.Resource
import com.example.noteapp.core.UIState
import com.example.noteapp.data.repository.NoteRepositoryImpl
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.usecase.AddNoteUseCase
import com.example.noteapp.domain.usecase.DeleteNoteUseCase
import com.example.noteapp.domain.usecase.EditNoteUseCase
import com.example.noteapp.domain.usecase.GeAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : BaseViewModel() {

    private val _addNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val addNoteState = _addNoteState.asStateFlow()

    fun addNote(note: Note) {
        viewModelScope.launch {
            addNoteUseCase.addNote(note).collect{ resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _addNoteState.value = UIState.Loading()
                    }
                    is Resource.Error -> {
                        _addNoteState.value = UIState.Error(resource.message!!)
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            _addNoteState.value = UIState.Success(resource.data)
                        }
                    }
                }
            }
        }
    }
}