package com.example.tuner

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.database.getIntOrNull


class DbBasicTunerHandler(var context: Context) :
    SQLiteOpenHelper(context, Companion.DATABASE_NAME, null, 1) {

    val all: Cursor
        get() = this.writableDatabase.query(TABLE_NAME, null, null, null, null, null, null)


    fun create() {
        onCreate(this.writableDatabase)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS  " + Companion.TABLE_NAME + " (" +
                Companion.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Companion.COL_NAME + " VARCHAR(255)," +
                Companion.COL_INSTRUMENT_ID + " INT(9)," +
                Companion.COL_ORDER + " INT(9)," +
                Companion.COL_TUNE_1 + " INT(9)," +
                Companion.COL_TUNE_2 + " INT(9)," +
                Companion.COL_TUNE_3 + " INT(9)," +
                Companion.COL_TUNE_4 + " INT(9)," +
                Companion.COL_TUNE_5 + " INT(9)," +
                Companion.COL_TUNE_6 + " INT(9)," +
                Companion.COL_TUNE_7 + " INT(9)," +
                Companion.COL_TUNE_8 + " INT(9)," +
                Companion.COL_TUNE_9 + " INT(9)," +
                Companion.COL_TUNE_10 + " INT(9)" +
                ")"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(basicTunerModel: BasicTunerModel) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Companion.COL_ID, basicTunerModel.id)
        cv.put(Companion.COL_NAME, basicTunerModel.name)
        cv.put(Companion.COL_INSTRUMENT_ID, basicTunerModel.instrumentId)
        cv.put(Companion.COL_ORDER, basicTunerModel.order)
        cv.put(Companion.COL_TUNE_1, basicTunerModel.tune1)
        cv.put(Companion.COL_TUNE_2, basicTunerModel.tune2)
        cv.put(Companion.COL_TUNE_3, basicTunerModel.tune3)
        cv.put(Companion.COL_TUNE_4, basicTunerModel.tune4)
        cv.put(Companion.COL_TUNE_5, basicTunerModel.tune5)
        cv.put(Companion.COL_TUNE_6, basicTunerModel.tune6)
        cv.put(Companion.COL_TUNE_7, basicTunerModel.tune7)
        cv.put(Companion.COL_TUNE_8, basicTunerModel.tune8)
        cv.put(Companion.COL_TUNE_9, basicTunerModel.tune9)
        cv.put(Companion.COL_TUNE_10, basicTunerModel.tune10)

        val result = db.insert(Companion.TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun setHighestOrder(id: Int) {
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
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun getAll(): ArrayList<BasicTunerModel>? {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $Companion.TABLE_NAME", null)
        } catch (e: SQLiteException) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()

        return cursor?.let { getResult(it) }
    }

    fun getByInstrumentId(id: Int): ArrayList<BasicTunerModel> {

        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "SELECT * FROM ${Companion.TABLE_NAME} " +
                     " WHERE instrument_id IN (?)" +
                     " ORDER BY custom_order DESC",
                arrayOf<String>(id.toString()
                )
            )
        } catch (e: SQLiteException) {
        }

        val result = cursor?.let { getResult(it) }

        if (result !== null) {
            return result;
        }

        db.close()
        return ArrayList()
    }

    private fun getResult(cursor: Cursor): ArrayList<BasicTunerModel> {
        val basicTuners = ArrayList<BasicTunerModel>()

        var id: Int
        var name: String
        var instrumentId: Int
        var order: Int
        var tune1: Int?
        var tune2: Int?
        var tune3: Int?
        var tune4: Int?
        var tune5: Int?
        var tune6: Int?
        var tune7: Int?
        var tune8: Int?
        var tune9: Int?
        var tune10: Int?
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(Companion.COL_ID))
                name = cursor.getString(cursor.getColumnIndex(Companion.COL_NAME))
                instrumentId =
                    cursor.getInt(cursor.getColumnIndex(Companion.COL_INSTRUMENT_ID))
                order = cursor.getInt(cursor.getColumnIndex(Companion.COL_ORDER))
                tune1 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_1))
                tune2 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_2))
                tune3 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_3))
                tune4 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_4))
                tune5 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_5))
                tune6 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_6))
                tune7 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_7))
                tune8 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_8))
                tune9 = cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_9))
                tune10 =
                    cursor.getIntOrNull(cursor.getColumnIndex(Companion.COL_TUNE_10))

                basicTuners.add(
                    BasicTunerModel(
                        id,
                        name,
                        instrumentId,
                        order,
                        tune1,
                        tune2,
                        tune3,
                        tune4,
                        tune5,
                        tune6,
                        tune7,
                        tune8,
                        tune9,
                        tune10
                    )
                )
                cursor.moveToNext()
            }
        }

        return basicTuners
    }

    companion object {
        const val TABLE_NAME = "BasicTuner"
        const val DATABASE_NAME = "tuner"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_ORDER = "custom_order"
        const val COL_INSTRUMENT_ID = "instrument_id"
        const val COL_TUNE_1 = "tun_1"
        const val COL_TUNE_2 = "tun_2"
        const val COL_TUNE_3 = "tun_3"
        const val COL_TUNE_4 = "tun_4"
        const val COL_TUNE_5 = "tun_5"
        const val COL_TUNE_6 = "tun_6"
        const val COL_TUNE_7 = "tun_7"
        const val COL_TUNE_8 = "tun_8"
        const val COL_TUNE_9 = "tun_9"
        const val COL_TUNE_10 = "tun_10"
    }

    fun logAll() {
        val csr = all
        var sb = StringBuilder()
        while (csr.moveToNext()) {
            sb = StringBuilder().append("Row is " + csr.position.toString())
            sb.append("\n\torder is :").append(csr.getString(csr.getColumnIndex(COL_ORDER)))
            Log.d("LOGDATA", sb.toString())
        }
    }
}