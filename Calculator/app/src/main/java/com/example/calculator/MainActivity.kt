package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openFrameLayoutCalculator(view: View) {
        startActivity(Intent(this, FrameLayoutCalculator::class.java))
    }

    fun openLinearLayoutCalculator(view: View) {
        startActivity(Intent(this, LinearLayoutCalculator::class.java))
    }

    fun openConstraintLayoutCalculator(view: View) {
        startActivity(Intent(this, ConstraintLayoutCalculator::class.java))
    }
}
