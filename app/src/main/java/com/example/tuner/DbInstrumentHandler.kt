package com.example.tuner

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import es.dmoral.toasty.Toasty


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
                Companion.COL_NAME + " VARCHAR(255), " +
                Companion.COL_ORDER + " INT(9))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(instrumentModel: InstrumentModel, silentMode: Boolean = false) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Companion.COL_ID, instrumentModel.id)
        cv.put(Companion.COL_NAME, instrumentModel.name)
        cv.put(Companion.COL_ORDER, instrumentModel.order)
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

    fun getCurrentInstrument(): InstrumentModel? {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "SELECT * FROM ${Companion.TABLE_NAME} WHERE custom_order = (" +
                        "SELECT MAX(custom_order) as co " +
                        " FROM ${Companion.TABLE_NAME} " +
                        " ORDER BY co DESC " +
                        " LIMIT 1" +
                        ") ",
                null
            )
        } catch (e: SQLiteException) {
            Toasty.error(context, "Failed", Toast.LENGTH_SHORT).show()
        }

        return cursor?.let { getResult(it).first() }
    }

    fun setTheHighestOrder(id: Int) {
        val db = this.writableDatabase
        try {
            db.execSQL(
                "UPDATE ${Companion.TABLE_NAME} " +
                        "SET custom_order=(" +
                        "(SELECT MAX(custom_order) as co " +
                        "FROM ${Companion.TABLE_NAME} " +
                        "ORDER BY co " +
                        "DESC LIMIT 1)+1" +
                        ") " +
                        "WHERE id = ?",
                arrayOf(id.toString())
            )

        } catch (e: SQLiteException) {
            Toasty.error(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun getAll(): ArrayList<InstrumentModel> {

        val db = this.writableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM ${Companion.TABLE_NAME} ORDER by custom_order DESC", null)
            return getResult(cursor)
        } catch (e: SQLiteException) {
        }

        return ArrayList()
    }

    private fun getResult(cursor: Cursor): ArrayList<InstrumentModel> {
        val instruments = ArrayList<InstrumentModel>()

        var id: Int
        var name: String
        var order: Int
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(Companion.COL_ID))
                name = cursor.getString(cursor.getColumnIndex(Companion.COL_NAME))
                order = cursor.getInt(cursor.getColumnIndex(Companion.COL_ORDER))

                instruments.add(InstrumentModel(id, name, order))
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
        const val COL_ORDER = "custom_order"
    }
}