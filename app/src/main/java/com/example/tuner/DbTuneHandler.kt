package com.example.tuner

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import es.dmoral.toasty.Toasty


class DbTuneHandler(var context: Context) :
    SQLiteOpenHelper(context, Companion.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS  " + Companion.TABLE_NAME + " (" +
                Companion.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Companion.COL_NAME + " VARCHAR(255)," +
                Companion.COL_EXACT_FREQUENCY + " DOUBLE" +
                ")"
        db?.execSQL(createTable)
    }
    fun create() {
        onCreate(this.writableDatabase)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(tuneModel: TuneModel, silentMode: Boolean = false) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Companion.COL_ID, tuneModel.id)
        cv.put(Companion.COL_NAME, tuneModel.name)
        cv.put(Companion.COL_EXACT_FREQUENCY, tuneModel.exactFrequency)
        val result = db.insert(Companion.TABLE_NAME, null, cv)
        if (!silentMode) {
            if (result == (-1).toLong()) {
                Toasty.error(context, "Failed", Toast.LENGTH_SHORT).show()
            } else {
                Toasty.success(context, "Success", Toast.LENGTH_SHORT).show()
            }
        }
        db.close()
    }

    fun getAll(): ArrayList<TuneModel> {
        val tunes = ArrayList<TuneModel>()

        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM ${Companion.TABLE_NAME}", null)
        } catch (e: SQLiteException) {
        }

        var id: Int
        var name: String
        var exactFrequency: Double
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(Companion.COL_ID))
                name = cursor.getString(cursor.getColumnIndex(Companion.COL_NAME))
                exactFrequency =
                    cursor.getDouble(cursor.getColumnIndex(Companion.COL_EXACT_FREQUENCY))
                tunes.add(TuneModel(id, name, exactFrequency))
                cursor.moveToNext()
            }
        }

        return tunes
    }

    companion object {
        const val DATABASE_NAME = "tuner"
        const val TABLE_NAME = "Tune"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_FREQUENCY_TO = "frequency_to"
        const val COL_EXACT_FREQUENCY = "exact_frequency"
    }
}