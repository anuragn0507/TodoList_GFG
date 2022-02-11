package com.anurag.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter (var toDoList: MutableList<ToDo>, val toDoInterface: ToDoInterface):RecyclerView.Adapter<ToDoAdapter.ToDoViewholder>() {
    class ToDoViewholder(itemview: View): RecyclerView.ViewHolder(itemview){
        var nameText: TextView = itemview.findViewById(R.id.title)
        var editButton: ImageView = itemview.findViewById(R.id.edit_icon)
        var deleteButton: ImageView = itemview.findViewById(R.id.delete_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewholder {
        val itemview = LayoutInflater.from( parent.context ).inflate(R.layout.to_do_item, parent,false)
        return ToDoViewholder(itemview)
    }

    override fun onBindViewHolder(holder: ToDoViewholder, position: Int) {
        val todo = toDoList[position]
        holder.nameText.text = todo.name

        holder.editButton.setOnClickListener{
          toDoInterface.updateToDoText(toDoList[position])
        }

        holder.deleteButton.setOnClickListener {
            toDoInterface.onDeleteTodo(todo, position )
        }

    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun setList(list:MutableList<ToDo>){
        toDoList = list
        notifyDataSetChanged()
    }

    fun deleteItem(todo:ToDo, position:Int){
        toDoList.remove(todo)
        notifyDataSetChanged()
    }


}