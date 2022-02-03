package com.anurag.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateToDoActivity : AppCompatActivity() {
    lateinit var toDoDatabase: ToDoDatabase
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_to_do)

        toDoDatabase = Room.databaseBuilder(applicationContext,ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

        editText = findViewById(R.id.to_do_edit_text)

        val saveButton : Button = findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            val enteredText = editText.text.toString()

            if(TextUtils.isEmpty(enteredText)){
                Toast.makeText(this,"Text should not be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val todo= ToDo()
            todo.name  = enteredText
            insertRow(todo)
        }

    }

    private fun insertRow(toDo: ToDo){
        GlobalScope.launch (Dispatchers.IO){
            toDoDatabase.toDoAppDao().insertTodo(toDo)
            println("Insertion thread: ${Thread.currentThread().name}")

            launch(Dispatchers.Main){
            println("For the intent : ${Thread.currentThread().name}")

            }
        }

    }
}