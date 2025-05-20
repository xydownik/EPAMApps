package com.example.contactapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.contact_name)
        val phone: TextView = view.findViewById(R.id.contact_phone)
        val image: ImageView = view.findViewById(R.id.contact_image)
    }