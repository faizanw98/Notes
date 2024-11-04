package com.devfaizan.notes.editnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.devfaizan.notes.R
import com.devfaizan.notes.databinding.FragmentEditNoteBinding
import com.devfaizan.notes.model.Note
import com.devfaizan.notes.model.NoteDatabase

class EditNoteFragment : Fragment() {
    private lateinit var binding: FragmentEditNoteBinding
    private val args: EditNoteFragmentArgs by navArgs()
    private lateinit var editNoteViewModel: EditNoteViewModel
    private var noteId:Long = -1L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_note,container,false)
        val dataSource = NoteDatabase.getInstance(requireContext()).noteDao
        val editNoteViewModelFactory = EditNoteViewModelFactory(dataSource,noteId = args.noteID)
        editNoteViewModel = ViewModelProvider(this,editNoteViewModelFactory)[EditNoteViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = editNoteViewModel
        binding.menu.inflateMenu(R.menu.note_edit_menu)
        NavigationUI.setupWithNavController(binding.menu,findNavController())
        noteId = args.noteID
        binding.menu.setOnMenuItemClickListener {
            saveNote()
            findNavController().navigateUp()
        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback {
            saveNote()
            findNavController().navigateUp()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        binding.menu.setNavigationOnClickListener {
            saveNote()
            findNavController().navigateUp()
        }
            return binding.root
        }

    private fun saveNote():Boolean{
            if (noteId!= -1L) {
                val note = Note(
                    noteId,
                    binding.titleEdit.text.toString(),
                    binding.textEdit.text.toString()
                )
                editNoteViewModel.update(note)

            } else {
                val note = Note(
                    noteTitle = binding.titleEdit.text.toString(),
                    noteText = binding.textEdit.text.toString()
                )
                if(!note.noteTitle.matches(Regex("^\\s*\$"))||!note.noteText.matches(Regex("^\\s*\$"))){
                    noteId = editNoteViewModel.insert(note)
                }

            }
        return true
    }
}
