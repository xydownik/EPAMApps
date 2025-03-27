package com.example.todolist.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.data.DBHelper
import com.example.todolist.databinding.ActivityEditListBinding
import com.example.todolist.databinding.ItemTodoBinding

data class EditItemHolder(
    val binding: ItemTodoBinding,
    var itemId: Long? = null
)
class EditListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditListBinding
    private lateinit var dbHelper: DBHelper
    private val itemHolders = mutableListOf<EditItemHolder>()

    private var isEditMode = false
    private var listId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        if (intent.hasExtra(LIST_ID)) {
            isEditMode = true
            listId = intent.getLongExtra(LIST_ID, -1L)
            binding.editTextListName.setText(intent.getStringExtra(LIST_NAME) ?: EMPTY_STRING)
            loadExistingItems()
        }

        binding.buttonAddItem.setOnClickListener {
            addNewItemRow(null, EMPTY_STRING, false)
        }

        binding.buttonSaveList.setOnClickListener {
            saveList()
        }
    }

    private fun loadExistingItems() {
        val items = dbHelper.getItemsForList(listId)
        for ((itemId, itemText, checked) in items) {
            addNewItemRow(itemId, itemText, checked)
        }
    }

    private fun addNewItemRow(itemId: Long?, text: String, checked: Boolean) {
        val itemBinding = ItemTodoBinding.inflate(layoutInflater)
        itemBinding.editTextItem.setText(text)
        itemBinding.checkBoxItem.isChecked = checked
        val holder = EditItemHolder(itemBinding, itemId)
        itemHolders.add(holder)
        binding.linearLayoutItems.addView(itemBinding.root)

        itemBinding.buttonDeleteItem.setOnClickListener {
            holder.itemId?.let { id ->
                dbHelper.deleteItem(id)
            }
            binding.linearLayoutItems.removeView(itemBinding.root)
            itemHolders.remove(holder)
        }
    }

    private fun saveList() {
        val listName = binding.editTextListName.text.toString().trim()
        if (listName.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_a_list_name), Toast.LENGTH_SHORT).show()
            return
        }

        if (isEditMode) {
            dbHelper.updateListName(listId, listName)
            for (holder in itemHolders) {
                val itemText = holder.binding.editTextItem.text.toString().trim()
                val isChecked = holder.binding.checkBoxItem.isChecked
                if (itemText.isNotEmpty()) {
                    if (holder.itemId != null) {
                        dbHelper.updateItem(holder.itemId!!, itemText, isChecked)
                    } else {
                        dbHelper.insertItem(listId, itemText, isChecked)
                    }
                }
            }
            Toast.makeText(this, getString(R.string.list_updated), Toast.LENGTH_SHORT).show()
        } else {
            val newListId = dbHelper.insertList(listName)
            for (holder in itemHolders) {
                val itemText = holder.binding.editTextItem.text.toString().trim()
                if (itemText.isNotEmpty()) {
                    dbHelper.insertItem(newListId, itemText, holder.binding.checkBoxItem.isChecked)
                }
            }
            Toast.makeText(this, getString(R.string.list_saved), Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
