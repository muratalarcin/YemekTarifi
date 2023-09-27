package com.muratalarcin.yemektarifi.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

abstract class FavoriteItemsDatabase<Specification>(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "FavoriteItems.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "favorite_items"
        private const val COLUMN_ID = "id"
        private const val COLUMN_UUID = "uuid"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_UUID TEXT, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun addToFavorites(specification: Specification) {
        val values = ContentValues()
        values.put(COLUMN_UUID, specification.uuid)
        values.put(COLUMN_NAME, specification.name)
        values.put(COLUMN_DESCRIPTION, specification.description)

        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun removeFromFavorites(specification: Specification) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_UUID = ?", arrayOf(specification.uuid))
        db.close()
    }

    fun getFavoriteItems(): List<Specification> {
        val favoriteItems = mutableListOf<Specification>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val uuid = it.getString(it.getColumnIndex(COLUMN_UUID))
                    val name = it.getString(it.getColumnIndex(COLUMN_NAME))
                    val description = it.getString(it.getColumnIndex(COLUMN_DESCRIPTION))
                    val specification = Specification(uuid, name, description)
                    favoriteItems.add(specification)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        db.close()

        return favoriteItems
    }
}