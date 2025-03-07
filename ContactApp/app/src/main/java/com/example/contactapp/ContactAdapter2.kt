package com.example.contactapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter2(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter2.ContactViewHolder>() {

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.contact_name)
        val phone: TextView = view.findViewById(R.id.contact_phone)
        val image: ImageView = view.findViewById(R.id.contact_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item_grid, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name
        holder.phone.text = contact.phoneNumber
        holder.image.setImageURI(contact.photoUri?.toUri() ?: Uri.EMPTY)

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.phoneNumber}"))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = contacts.size
}
