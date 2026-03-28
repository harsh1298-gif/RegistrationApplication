package com.example.registrationapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "StudentDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE student (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "phone TEXT," +
                "gender TEXT," +
                "course TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS student")
        onCreate(db)
    }

    // INSERT
    fun insertData(name: String, phone: String, gender: String, course: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("phone", phone)
        values.put("gender", gender)
        values.put("course", course)
        val result = db.insert("student", null, values)
        return result != -1L
    }

    // READ
    fun getAllData() = readableDatabase.rawQuery("SELECT * FROM student", null)

    // UPDATE
    fun updateData(id: String, name: String, phone: String, gender: String, course: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("phone", phone)
        values.put("gender", gender)
        values.put("course", course)
        db.update("student", values, "id=?", arrayOf(id))
        return true
    }

    // DELETE
    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete("student", "id=?", arrayOf(id))
    }
}