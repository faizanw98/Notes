package com.devfaizan.notes.notesview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfaizan.notes.databinding.NoteContainerBinding
import com.devfaizan.notes.model.Note
import com.devfaizan.notes.notesview.NotesViewAdapter.ViewHolder

class NotesViewAdapter(private val listener: OnItemButtonClickListener): ListAdapter<Note,ViewHolder>(NotesDiffCallBack()) {
    private val selectedItems = mutableSetOf<Int>()
    class ViewHolder private constructor(val binding: NoteContainerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note, isSelected: Boolean, selectedItems: MutableSet<Int>, listener: OnItemButtonClickListener, position: Int) {
            binding.note = item
            binding.noteContainer.isChecked = isSelected
            binding.root.setOnClickListener {
               if(selectedItems.isEmpty()){
                   val navController = Navigation.findNavController(binding.root)
                   navController.navigate(NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(item.noteId))
               }else{
                   listener.toggleSelectionMode(position)
                   binding.noteContainer.isSelected = isSelected
               }
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
        holder.bind(item,selectedItems.contains(position),selectedItems,listener,position)
        holder.itemView.setOnLongClickListener{
            listener.toggleSelectionMode(position)
            holder.itemView.isSelected = selectedItems.contains(position)
            true
        }
    }

     fun toggleSelection(position: Int) {
        if (selectedItems.contains(position)){
            selectedItems.remove(position)
        }else{
            selectedItems.add(position)
        }
        notifyItemChanged(position)
    }
    fun getSelectedItems():List<Note>{
        val list = selectedItems.map {getItem(it)}
        return list
    }
    fun clearSelection(){
        selectedItems.clear()
        notifyDataSetChanged()
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