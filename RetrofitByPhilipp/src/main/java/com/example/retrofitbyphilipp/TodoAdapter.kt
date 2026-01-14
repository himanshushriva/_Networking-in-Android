package com.example.retrofitbyphilipp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitbyphilipp.databinding.ItemTodoBinding
import com.example.retrofitbyphilipp.model.Todo

class TodoAdapter(/*context: Context, private val todos: List<Todo>*/) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    //lateinit var binding: ItemTodoBinding

    inner class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<Todo>
        get() = differ.currentList
        set(value) { differ.submitList(value) }





    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo/*R.id.item_todo*/, parent, false)
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.apply {
            val todo = todos[position]
            tvTitle.text = todo.title
            cbDone.isChecked = todo.completed
        }
        /*holder.binding.tvTitle.text = todoItem.title
        holder.binding.cbDone.isChecked = todoItem.completed*/

    }

    override fun getItemCount(): Int = todos.size

}