package com.example.todolist.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "ToDoPrefs"
        const val KEY_CHECKED_COLOR = "checked_color"
        const val CHECKED_COLOR_GREEN = "#00FF00"
        const val CHECKED_COLOR_BLUE = "#0000FF"
        const val CHECKED_COLOR_RED = "#FF0000"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val savedColor = sharedPreferences.getString(KEY_CHECKED_COLOR, CHECKED_COLOR_GREEN)
        if (savedColor == CHECKED_COLOR_GREEN) {
            binding.radioGroupColors.check(R.id.radioGreen)
        } else if (savedColor == CHECKED_COLOR_BLUE){
            binding.radioGroupColors.check(R.id.radioBlue)
        } else {
            binding.radioGroupColors.check(R.id.radioRed)
        }

        binding.radioGroupColors.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioGreen -> {
                    sharedPreferences.edit().putString(KEY_CHECKED_COLOR, CHECKED_COLOR_GREEN).apply()
                }
                R.id.radioBlue -> {
                    sharedPreferences.edit().putString(KEY_CHECKED_COLOR, CHECKED_COLOR_BLUE).apply()
                }
                R.id.radioRed -> {
                    sharedPreferences.edit().putString(KEY_CHECKED_COLOR, CHECKED_COLOR_RED).apply()
                }
            }
        }


    }
}
