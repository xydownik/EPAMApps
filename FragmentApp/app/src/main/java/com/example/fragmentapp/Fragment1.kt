package com.example.fragmentapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentapp.databinding.Fragment1Binding
import kotlin.random.Random


class Fragment1 : Fragment() {

    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonChangeColor.setOnClickListener {
            sharedViewModel.changeBackgroundColors()
        }

        binding.buttonSwapFragments.setOnClickListener {
            sharedViewModel.swapFragments()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
