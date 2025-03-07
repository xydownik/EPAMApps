package com.example.contactapp

import android.content.Context
import android.provider.ContactsContract

object ContactHelper {
    fun getContacts(context: Context): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (it.moveToNext()) {
                val name = if (nameIndex != -1) it.getString(nameIndex) else "Unknown"
                val phone = if (phoneIndex != -1) it.getString(phoneIndex) else "No number"
                val photoUri = if (photoIndex != -1) it.getString(photoIndex) else null

                contactList.add(Contact(name, phone, photoUri))
            }
        }

        return contactList
    }
}
