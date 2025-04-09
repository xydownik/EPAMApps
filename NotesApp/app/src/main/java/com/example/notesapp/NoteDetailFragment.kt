package com.example.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentNoteDetailBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt("noteId") ?: -1
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        lifecycleScope.launch {
            val note = viewModel.getNoteById(noteId)

            binding.editTitle.setText(note.name)
            binding.noteDate.text = note.date
            binding.editText.setText(note.text)

            binding.deleteButton.setOnClickListener {
                viewModel.delete(note)
                findNavController().popBackStack()
            }

            binding.editButton.setOnClickListener {
                val updated = note.copy(
                    name = binding.editTitle.text.toString(),
                    text = binding.editText.text.toString(),
                    date = SimpleDateFormat(getString(R.string.yyyy_mm_dd_hh_mm), Locale.getDefault()).format(Date())
                )
                viewModel.update(updated)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
