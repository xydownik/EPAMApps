package com.example.fragmentapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider



class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentContainer1, Fragment1())
                add(R.id.fragmentContainer2, Fragment2())
                add(R.id.fragmentContainer3, Fragment3())
            }
        }

        sharedViewModel.isSwapped.observe(this) { isSwapped ->
            swapFragments(isSwapped)
        }
    }

    private fun swapFragments(isSwapped: Boolean) {
        val fragmentManager = supportFragmentManager
        fragmentManager.commit {
            replace(R.id.fragmentContainer2, if (isSwapped) Fragment3() else Fragment2())
            replace(R.id.fragmentContainer3, if (isSwapped) Fragment2() else Fragment3())
        }
    }

}
