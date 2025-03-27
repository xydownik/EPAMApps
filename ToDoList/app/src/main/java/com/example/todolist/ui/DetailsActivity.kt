package com.example.todolist.ui

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.data.DBHelper
import com.example.todolist.databinding.ActivityDetailsBinding
import com.example.todolist.ui.SettingsActivity
const val LIST_ID = "LIST_ID"
const val LIST_NAME = "LIST_NAME"
const val EMPTY_STRING = ""
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var dbHelper: DBHelper
    private var listId: Long = -1L
    private var listName: String = EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        listId = intent.getLongExtra(LIST_ID, -1L)
        listName = intent.getStringExtra(LIST_NAME) ?: getString(R.string.unnamed_list)
        binding.textViewListName.text = listName

        loadListItems()

        binding.buttonEditList.setOnClickListener {
            val intent = Intent(this, EditListActivity::class.java).apply {
                putExtra(LIST_ID, listId)
                putExtra(LIST_NAME, listName)
            }
            startActivity(intent)
        }

        binding.buttonDeleteList.setOnClickListener {
            dbHelper.deleteList(listId)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        loadListItems()
    }

    private fun loadListItems() {
        val sharedPreferences = getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE)
        val savedColorString = sharedPreferences.getString(SettingsActivity.KEY_CHECKED_COLOR, SettingsActivity.CHECKED_COLOR_GREEN)
        val checkedColor = Color.parseColor(savedColorString)

        binding.linearLayoutDetailsItems.removeAllViews()
        val items = dbHelper.getItemsForList(listId)
        for ((itemId, itemText, checked) in items) {
            val itemBinding = com.example.todolist.databinding.ItemDetailBinding.inflate(layoutInflater, binding.linearLayoutDetailsItems, false)
            itemBinding.checkBoxDetailItem.isChecked = checked
            itemBinding.textViewDetailItem.text = itemText

            if (checked) {
                itemBinding.checkBoxDetailItem.buttonTintList = ColorStateList.valueOf(checkedColor)
            } else {
                itemBinding.checkBoxDetailItem.buttonTintList = ColorStateList.valueOf(Color.GRAY)
            }

            itemBinding.checkBoxDetailItem.setOnCheckedChangeListener { _, isChecked ->
                dbHelper.updateItemChecked(itemId, isChecked)
                itemBinding.checkBoxDetailItem.buttonTintList =
                    ColorStateList.valueOf(if (isChecked) checkedColor else Color.GRAY)
            }

            binding.linearLayoutDetailsItems.addView(itemBinding.root)
        }
    }

}
