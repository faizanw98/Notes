package com.devfaizan.notes.notesview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfaizan.notes.databinding.NoteContainerBinding
import com.devfaizan.notes.model.Note
import com.devfaizan.notes.notesview.NotesViewAdapter.ViewHolder

class NotesViewAdapter(private val listener: OnItemButtonClickListener): ListAdapter<Note,ViewHolder>(NotesDiffCallBack()) {
    class ViewHolder private constructor(val binding: NoteContainerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note,buttonClickListener: OnItemButtonClickListener) {
            binding.note = item
            binding.deleteButton.setOnClickListener{
                buttonClickListener.onItemButtonClick(item.noteId)
            }
            binding.root.setOnClickListener {
               val navController = Navigation.findNavController(binding.root)
                navController.navigate(NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(item.noteId))
            }
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteContainerBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, buttonClickListener = listener)
    }


    class NotesDiffCallBack: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

}