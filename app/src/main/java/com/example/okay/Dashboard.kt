package com.example.okay

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.okay.DTO.Todo
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

        lateinit var DataBase: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar_dh)
        title = "Dashboard"

        DataBase = DataBase(this)
        rv_dashboard.layoutManager = LinearLayoutManager (this)
        refreshList()

        fabDashboard.setOnClickListener{
            val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_dashboard,null)
            val toDoName : EditText =view.findViewById<EditText>(R.id.etTodo)

            dialog.setView(view)
            dialog.setNegativeButton("Add") { _: DialogInterface, _: Int ->
                if (toDoName.text.isNotEmpty()) {
                    val todo = Todo()
                    todo.name = toDoName.text.toString()
                    DataBase.addTodo(todo)
                }
            }
            dialog.setNegativeButton("cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }

    }


    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList() {
        rv_dashboard.adapter = DashboardAdapter(this, DataBase.getTodo())
    }

    class DashboardAdapter(val context: Context, val list: MutableList<Todo>): RecyclerView.Adapter<DashboardAdapter.ViewHolder>(){
        class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
            val toDoName: TextView = v.findViewById(R.id.tv_todoName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.dasboard_child, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.toDoName.text = list[position].name
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }


}