package com.example.noteapp.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.core.BaseFragment
import com.example.noteapp.data.local.NoteDao
import com.example.noteapp.databinding.FragmentNoteListBinding
import com.example.noteapp.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : BaseFragment(R.layout.fragment_note_list) {

    private val noteAdapter by lazy { NoteAdapter(this::onItemLongClick, this::onItemClick) }
    private val notesAdapter by lazy { NoteRecyclerAdapter(this::onItemLongClick, this::onItemClick) }
    private val viewModel by viewModels<NoteListViewModel>()
    private val binding by viewBinding(FragmentNoteListBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupRequest()
        setupObservers()
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
        viewModel.editNoteState.collectState(
            onLoading = { Toast.makeText(requireContext(),"Item touched", Toast.LENGTH_SHORT).show() },
            onError = { },
            onSuccess = {
                noteAdapter.edit(it)
                Toast.makeText(requireContext(),"Item touched", Toast.LENGTH_SHORT).show()
            }
        )
        viewModel.getAllNotesState.collectState(
            onLoading = { },
            onError = { },
            onSuccess = { data ->
//                notesAdapter.submitList(data)
                noteAdapter.setData(data)
                Toast.makeText(requireContext(),"Notes updated successfully", Toast.LENGTH_SHORT).show()
            }
        )
        viewModel.deleteNoteState.collectState(
            onLoading = { },
            onError = { },
            onSuccess = {
                Toast.makeText(requireContext(),"Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setupClickListeners() {
        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.addNoteFragment)
        }
    }

    private fun onItemLongClick(note: Note){
        viewModel.deleteNote(note)
        noteAdapter.delete(note)
    }

    private fun onItemClick(note: Note){
        viewModel.editNote(note)
        noteAdapter.edit(note)
    }
}