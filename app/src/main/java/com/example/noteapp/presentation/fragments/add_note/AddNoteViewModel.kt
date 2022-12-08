package com.example.noteapp.presentation.fragments.add_note

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.noteapp.core.BaseViewModel
import com.example.noteapp.core.Resource
import com.example.noteapp.core.UIState
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.usecase.AddNoteUseCase
import com.example.noteapp.domain.usecase.EditNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase

    ) : BaseViewModel() {

    private val _addNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val addNoteState = _addNoteState.asStateFlow()

    private val _editNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val editNoteState = _editNoteState.asStateFlow()

    fun editNote(note: Note) {
        Log.d("-----", "editNote")
        editNoteUseCase.editNote(note).collectData(_editNoteState)
    }

    fun addNote(note: Note) {
        //This is way less code
        addNoteUseCase.addNote(note).collectData(_addNoteState)
        //This is method without using extension in BaseViewModel
//        viewModelScope.launch(Dispatchers.IO) {
//            addNoteUseCase.addNote(note).collect{ resource ->
//                when(resource) {
//                    is Resource.Loading -> {
//                        _addNoteState.value = UIState.Loading()
//                    }
//                    is Resource.Error -> {
//                        _addNoteState.value = UIState.Error(resource.message!!)
//                    }
//                    is Resource.Success -> {
//                        if (resource.data != null) {
//                            _addNoteState.value = UIState.Success(resource.data)
//                        }
//                    }
//                }
//            }
//        }
    }
}