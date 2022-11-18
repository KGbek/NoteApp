package com.example.noteapp.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BaseFragment : Fragment() {

    protected fun <T> StateFlow<UIState<T>>.collectState(
        onError: (message: String) -> Unit,
        onLoading: () -> Unit,
        onSuccess: (data: T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                this@collectState.collect{ state ->
                    when(state) {
                        is UIState.Loading -> {
                            onLoading()
                        }
                        is UIState.Error -> {
                            onError(state.error)
                        }
                        is UIState.Success -> {
                            onSuccess(state.data)
                        }
                        is UIState.Empty -> {}
                    }
                }
            }
        }
    }
}