package com.anurag.todolist

interface ToDoInterface {
    fun updateToDoText(toDo: ToDo)
    fun onDeleteTodo(todo: ToDo, position: Int)
}