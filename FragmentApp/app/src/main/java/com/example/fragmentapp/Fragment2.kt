package com.example.fragmentapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels



class Fragment2 : Fragment(R.layout.fragment_2) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBackgroundColor(sharedViewModel.fragment2Color.value ?: 0xFFFFFFFF.toInt())

        sharedViewModel.fragment2Color.observe(viewLifecycleOwner) { color ->
            view.setBackgroundColor(color)
        }
    }
}

