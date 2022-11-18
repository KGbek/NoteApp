package com.example.noteapp.presentation.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.NotesBinding
import com.example.noteapp.domain.model.Note

class NoteAdapter(
    private val onItemClick : (Note) -> Unit
): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var list = listOf<Note>()

    fun setData(list: List<Note>) {
        this.list = list
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: NotesBinding) :RecyclerView.ViewHolder(binding.root) {
        fun onBind(note: Note) = with(binding) {
            tvTitle.text = note.title
            tvDescription.text = note.description
            itemView.setOnLongClickListener {
                onItemClick.invoke(note)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NotesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}