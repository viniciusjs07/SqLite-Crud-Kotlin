package com.example.sqlite_crud.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite_crud.model.CentralModel

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "central.db"
        private const val TBL_CENTRAL = "tbl_central"
        private const val ID = "id"
        private const val NAME = "name"
        private const val MODEL = "model"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTblCentral =
            ("CREATE TABLE " + TBL_CENTRAL + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + MODEL + " TEXT" + ")")
        p0?.execSQL(createTblCentral)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_CENTRAL")
        onCreate(p0)
    }

    fun insertCentral(central: CentralModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, central.id)
        contentValues.put(NAME, central.name)
        contentValues.put(MODEL, central.model)

        val success = db.insert(TBL_CENTRAL, null, contentValues)
        db.close()
        return success
    }

    fun getAllCentral(): ArrayList<CentralModel> {
        val centralList: ArrayList<CentralModel> = ArrayList()
        val selectQuery = "SELECT * FROM  $TBL_CENTRAL"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var model: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                model = cursor.getString(cursor.getColumnIndex("model"))

                val central = CentralModel(id = id, name = name, model = model)
                centralList.add(central)
            } while (cursor.moveToNext())
        }
        return centralList
    }

    fun updateCentral(central: CentralModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, central.id)
        contentValues.put(NAME, central.name)
        contentValues.put(MODEL, central.model)

        val success = db.update(
            TBL_CENTRAL, contentValues, "id=" + central.id, null
        )
        db.close()
        return success
    }

    fun deleteCentralById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(
            TBL_CENTRAL, "id=$id", null
        )
        db.close()
        return success

    }
}