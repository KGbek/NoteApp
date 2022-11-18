package com.example.noteapp.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> Flow<Resource<T>>.collectData(_state: MutableStateFlow<UIState<T>>) {
        viewModelScope.launch {
            this@collectData.collect{ resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _state.value = UIState.Loading()
                    }
                    is Resource.Error -> {
                        _state.value = UIState.Error(resource.message!!)
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            UIState.Success(resource.data)
                        }
                    }
                }
            }
        }
    }

}