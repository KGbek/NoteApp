package com.example.noteapp.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.core.UIState
import com.example.noteapp.databinding.FragmentNoteListBinding
import com.example.noteapp.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private val noteAdapter by lazy { NoteAdapter(this::onItemLongClick) }
    private val viewModel by viewModels<NoteListViewModel>()
    private val binding by viewBinding(FragmentNoteListBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initialize()
        setupRequest()
        setupClickListeners()
    }

    private fun initialize() = with(binding){
        rvNotes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvNotes.adapter = noteAdapter
    }

    private fun setupRequest() {
        viewModel.getAllNotes()
    }

    private fun setupObservers() {
        //editNoteState
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editNoteState.collect { state ->
                    when (state) {
                        is UIState.Loading -> {

                        }
                        is UIState.Error -> {

                        }
                        is UIState.Success -> {

                        }
                        is UIState.Empty -> {

                        }
                    }
                }
            }
        }
        //getAllNotesState
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getAllNotesState.collect{ state ->
                    when(state) {
                        is UIState.Loading -> {

                        }
                        is UIState.Error -> {

                        }
                        is UIState.Success -> {
                            noteAdapter.setData(state.data)
                        }
                        is UIState.Empty -> {

                        }
                    }
                }
            }
        }
        //deleteNoteState
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteNoteState.collect{ state ->
                    when(state) {
                        is UIState.Success -> {
                            viewModel.getAllNotes()
                        }
                        is UIState.Loading -> {

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

    private fun setupClickListeners() {
        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.addNoteFragment)
            //Toast.makeText(requireContext(), "FAB is working", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onItemLongClick(note: Note){
        viewModel.deleteNote(note)
    }
}