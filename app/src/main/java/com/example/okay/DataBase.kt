package com.example.okay

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.okay.DTO.Todo

class DataBase(val context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        val createTodoTable: String = "CREATE TABLE $TABLE_TODO (" +
                    "$COL_ID integer PRIMARY KEY AUTOINCREMENT" +
                    "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP" +
                    "$COL_NAME varchar)";

        val createTodoItemTable: String = "CREATE TABLE $COL_TABLE_TODO_ITEM (" +
                    "$COL_ID integer PRIMARY KEY AUTOINCREMENT" +
                    "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP" +
                    "$COL_ID integer" +
                    "$COL_NAME varchar" +
                    "$COL_IS_COMPLETE integer )";

        db.execSQL(createTodoTable)
        db.execSQL(createTodoItemTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       }


    fun addTodo(todo: Todo) : Boolean{
        val db: SQLiteDatabase = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, todo.name)
        val Result = db.insert(TABLE_TODO,null,cv)
        return Result!=(-1).toLong()
    }

    fun getTodo(): MutableList<Todo> {
        val result: MutableList<Todo> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_TODO",null)

        if (queryResult.moveToFirst()){

            do {
                val todo = Todo()
                todo.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                todo.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                result.add(todo)

            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return  result
    }

}