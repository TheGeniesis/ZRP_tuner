package com.example.tuner

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val DATABASE_NAME = "tuner"
const val TABLE_NAME  = "Instrument"
const val COL_ID = "id"
const val COL_NAME = "name"

class DbInstrumentHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE "  + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(255))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(instrumentModel : InstrumentModel)
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, instrumentModel.name)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAll(): ArrayList<InstrumentModel> {
        val instruments = ArrayList<InstrumentModel>()

        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        } catch (e: SQLiteException) {
        }

        var id: Int
        var name: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                name = cursor.getString(cursor.getColumnIndex(COL_NAME))

                instruments.add(InstrumentModel(id, name))
                cursor.moveToNext()
            }
        }

        return instruments
    }
}