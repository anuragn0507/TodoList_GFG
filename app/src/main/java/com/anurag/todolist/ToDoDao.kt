package com.anurag.todolist

import androidx.room.Dao
import androidx.room.Insert


// DAO -> Data Access Objects
// CRUD -> Create, Read, Update, Delete

@Dao
interface ToDoDao {

    @Insert
    suspend fun insertTodo(todo: ToDo) : Long
}