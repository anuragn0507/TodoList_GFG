package com.anurag.todolist


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//GFG TodoList
class MainActivity : AppCompatActivity(), ToDoInterface {
    lateinit var recyclerView: RecyclerView
    lateinit var toDoAdapter: ToDoAdapter
    lateinit var toDoDatabase: ToDoDatabase

    companion object {
        const val PREVIOUS_TODO = "PreviousTodo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab: FloatingActionButton = findViewById(R.id.add_todo)

        fab.setOnClickListener {
            val intent = Intent(this, CreateToDoActivity::class.java)
            startActivity(intent)
        }

        toDoDatabase =
            Room.databaseBuilder(applicationContext, ToDoDatabase::class.java, ToDoDatabase.DB_NAME)
                .build()
        val list = mutableListOf<ToDo>()

        recyclerView = findViewById(R.id.recycler_view)
        toDoAdapter = ToDoAdapter(list, this)
        recyclerView.adapter = toDoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchToDoList()

    }

    private fun fetchToDoList() {
        GlobalScope.launch(Dispatchers.IO) {
            val todoList = toDoDatabase.toDoAppDao().fetchList()

            launch(Dispatchers.Main) {
                //UI updation needs to be done on the main thread
                toDoAdapter.setList(todoList)
            }
        }
    }

    override fun updateToDoText(todo: ToDo) {
        val intent = Intent(this, CreateToDoActivity::class.java)
        intent.putExtra(PREVIOUS_TODO, todo)
        startActivity(intent)
    }

    override fun onDeleteTodo(todo: ToDo, position: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            toDoDatabase.toDoAppDao().deleteTodo(todo)

            launch (Dispatchers.Main){
                toDoAdapter.deleteItem(todo,position)
            }
        }
    }
}

