package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.DBHelper
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: ArrayAdapter<String>
    private var listNames = mutableListOf<String>()
    private var listIds = mutableListOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        loadLists()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listNames)
        binding.listViewLists.adapter = adapter

        binding.listViewLists.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(LIST_ID, listIds[position])
                putExtra(LIST_NAME, listNames[position])
            }
            startActivity(intent)
        }

        binding.buttonAddList.setOnClickListener {
            startActivity(Intent(this, EditListActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadLists() {
        listNames.clear()
        listIds.clear()
        val lists = dbHelper.getAllLists()
        for ((id, name) in lists) {
            listIds.add(id)
            listNames.add(name)
        }
    }

    override fun onResume() {
        super.onResume()
        loadLists()
        adapter.notifyDataSetChanged()
    }
}
