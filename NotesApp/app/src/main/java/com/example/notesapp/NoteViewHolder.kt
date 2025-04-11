package com.example.notesapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteViewHolder(private val onClick: (Note) -> Unit, view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            itemView.findViewById<TextView>(R.id.noteTitle).text = note.name
            itemView.findViewById<TextView>(R.id.noteDate).text = note.date
            itemView.setOnClickListener { onClick(note) }
        }
    }