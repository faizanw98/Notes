package com.devfaizan.notes.notesview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.devfaizan.notes.R
import com.devfaizan.notes.databinding.FragmentNotesBinding
import com.devfaizan.notes.model.NoteDatabase


interface OnItemButtonClickListener {
    fun toggleSelectionMode(position: Int)
}


class NotesFragment : Fragment(), OnItemButtonClickListener{
    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesFragmentViewModel: NotesFragmentViewModel
    private var adapter: NotesViewAdapter = NotesViewAdapter(this)
    private var actionMode: ActionMode? = null
    private val actionModeCallback: ActionMode.Callback = object: ActionMode.Callback{
        override fun onCreateActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            mode?.menuInflater?.inflate(R.menu.contextual_menu,menu)
            return true
        }

        override fun onPrepareActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            return false
        }

        override fun onActionItemClicked(
            mode: ActionMode?,
            item: MenuItem?
        ): Boolean {
            when(item?.itemId){
                R.id.delete ->{
                    deleteSelectedNotes()
                    mode?.finish()
                    actionMode?.finish()
                    return true
                }
            }
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            adapter.clearSelection()
            actionMode = null
        }

        private fun deleteSelectedNotes() {
            val selectedNotes = adapter.getSelectedItems()
            for(item in selectedNotes){
                notesFragmentViewModel.delete(item.noteId)
            }
        }
    }
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
        binding.notesRecycleView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.notesRecycleView.adapter = adapter
        notesFragmentViewModel.notes?.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
                notesFragmentViewModel.deleteEmpty()
            }
        }
        return binding.root

    }
    override fun toggleSelectionMode(position: Int) {
        if (actionMode == null) {
            actionMode = (requireActivity() as  AppCompatActivity).startSupportActionMode(actionModeCallback)
        }
        adapter.toggleSelection(position)
        if (adapter.getSelectedItems().isNullOrEmpty()) {
            actionMode?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionMode?.finish()
    }
}
