package com.example.noteapp.presentation.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.data.model.NoteEntity

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.Viewholder>() {

    val list = listOf<NoteEntity>()


    class Viewholder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val tvDescription = itemView.findViewById<TextView>(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes, parent,false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val noteEntity = list[position]
        holder.tvTitle.text = noteEntity.title
        holder.tvDescription.text = noteEntity.description
    }

    override fun getItemCount(): Int {
        return list.size
    }

}