package com.example.tuner

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DbInstrumentHandler(var context: Context) :
    SQLiteOpenHelper(context, Companion.DATABASE_NAME, null, 1) {

    fun drop() {
        //uncomment it to remove database.
        // it will produce error with migration after call you need to comment it and run application again so new db will be created
             context.deleteDatabase(DATABASE_NAME)
        this.close()
             context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null)
        this.close()

    }
    fun create() {
        onCreate(this.writableDatabase)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS " + Companion.TABLE_NAME + " (" +
                Companion.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Companion.COL_NAME + " VARCHAR(255))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(instrumentModel: InstrumentModel) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Companion.COL_ID, instrumentModel.id)
        cv.put(Companion.COL_NAME, instrumentModel.name)
        val result = db.insert(Companion.TABLE_NAME, null, cv)
        println("wchod")
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        db.close()

    }

    fun getAll(): ArrayList<InstrumentModel> {
        val instruments = ArrayList<InstrumentModel>()


        val db = this.writableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM ${Companion.TABLE_NAME}", null)
        } catch (e: SQLiteException) {
        }

        var id: Int
        var name: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(Companion.COL_ID))
                name = cursor.getString(cursor.getColumnIndex(Companion.COL_NAME))

                instruments.add(InstrumentModel(id, name))
                cursor.moveToNext()
            }
        }

        return instruments
    }

    companion object {
        const val DATABASE_NAME = "tuner"
        const val TABLE_NAME = "Instrument"
        const val COL_ID = "id"
        const val COL_NAME = "name"
    }
}