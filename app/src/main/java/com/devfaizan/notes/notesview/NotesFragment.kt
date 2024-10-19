package com.devfaizan.notes.notesview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devfaizan.notes.R
import com.devfaizan.notes.databinding.FragmentNotesBinding
import com.devfaizan.notes.model.NoteDatabase
interface OnItemButtonClickListener {
    fun onItemButtonClick(noteId: Long)
}

class NotesFragment : Fragment(), OnItemButtonClickListener {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesFragmentViewModel: NotesFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NotesViewModelFactory(dataSource,application)
        notesFragmentViewModel = ViewModelProvider(this,viewModelFactory)[NotesFragmentViewModel::class.java]
        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment)
        }
        val adapter = NotesViewAdapter(this)
        binding.notesRecycleView.adapter = adapter
        binding.notesRecycleView.layoutManager = LinearLayoutManager(requireContext())
        notesFragmentViewModel.notes?.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
        return binding.root
    }

    override fun onItemButtonClick(noteId: Long) {
        notesFragmentViewModel.delete(noteId)
    }

}
