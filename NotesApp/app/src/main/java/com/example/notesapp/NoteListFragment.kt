package com.example.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.FragmentNoteListBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        adapter = NoteAdapter { note ->
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(note.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.addButton.setOnClickListener {
            lifecycleScope.launch {
                val newNote = Note(
                    name = getString(R.string.new_note),
                    text = "",
                    date = getCurrentDate()
                )
                viewModel.insert(newNote)

                val allNotes = viewModel.allNotes.value ?: return@launch
                val insertedNote = allNotes.firstOrNull { it.name == getString(R.string.new_note) && it.text.isEmpty() }

                insertedNote?.let {
                    val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(it.id)
                    findNavController().navigate(action)
                }
            }
        }
    }


    private fun getCurrentDate(): String {
        val format = SimpleDateFormat(getString(R.string.yyyy_mm_dd_hh_mm), Locale.getDefault())
        return format.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
