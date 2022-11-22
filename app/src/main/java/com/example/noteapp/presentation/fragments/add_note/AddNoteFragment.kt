package com.example.noteapp.presentation.fragments.add_note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.core.BaseFragment
import com.example.noteapp.core.UIState
import com.example.noteapp.databinding.FragmentAddNoteBinding
import com.example.noteapp.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteFragment : BaseFragment(R.layout.fragment_add_note) {

    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private val viewModel by viewModels<AddNoteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupRequest()
        setupObservers()
        setupClickListeners()
    }

    private fun initialize() {
    }

    private fun setupRequest() {
    }

    private fun setupObservers() {
//        viewModel.addNoteState.collectState(
//            onLoading = {
//
//            },
//            onError = {
//
//            },
//            onSuccess = {
//                findNavController().navigateUp()
//            }
//        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addNoteState.collect { state ->
                    when (state) {
                        is UIState.Loading -> {

                        }
                        is UIState.Success -> {
                            findNavController().navigateUp()
                        }
                        is UIState.Error -> {

                        }
                        is UIState.Empty -> {

                        }
                    }
                }
            }
        }
    }

    private fun setupClickListeners() = with(binding) {
        btnSave.setOnClickListener {
            viewModel.addNote(
                Note(
                    title = etTitle.text.toString(),
                    description = etDesciption.text.toString(),
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }
}