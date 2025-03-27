package com.example.todolist.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "ToDoLists.db"
        const val DATABASE_VERSION = 1
        const val TABLE_LISTS = "Lists"
        const val COLUMN_LIST_ID = "id"
        const val COLUMN_LIST_NAME = "name"
        const val TABLE_ITEMS = "Items"
        const val COLUMN_ITEM_ID = "id"
        const val COLUMN_ITEM_LIST_ID = "list_id"
        const val COLUMN_ITEM_TEXT = "item_text"
        const val COLUMN_ITEM_CHECKED = "checked"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createListsTable = """
            CREATE TABLE $TABLE_LISTS (
                $COLUMN_LIST_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_LIST_NAME TEXT NOT NULL
            );
        """.trimIndent()
        db.execSQL(createListsTable)

        val createItemsTable = """
            CREATE TABLE $TABLE_ITEMS (
                $COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_LIST_ID INTEGER,
                $COLUMN_ITEM_TEXT TEXT NOT NULL,
                $COLUMN_ITEM_CHECKED INTEGER DEFAULT 0,
                FOREIGN KEY($COLUMN_ITEM_LIST_ID) REFERENCES $TABLE_LISTS($COLUMN_LIST_ID)
            );
        """.trimIndent()
        db.execSQL(createItemsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LISTS")
        onCreate(db)
    }

    fun insertList(listName: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply { put(COLUMN_LIST_NAME, listName) }
        return db.insert(TABLE_LISTS, null, values)
    }

    fun insertItem(listId: Long, itemText: String, checked: Boolean): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ITEM_LIST_ID, listId)
            put(COLUMN_ITEM_TEXT, itemText)
            put(COLUMN_ITEM_CHECKED, if (checked) 1 else 0)
        }
        return db.insert(TABLE_ITEMS, null, values)
    }

    fun updateItemChecked(itemId: Long, checked: Boolean): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ITEM_CHECKED, if (checked) 1 else 0)
        }
        return db.update(TABLE_ITEMS, values, "$COLUMN_ITEM_ID=?", arrayOf(itemId.toString()))
    }

    fun updateListName(listId: Long, newName: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply { put(COLUMN_LIST_NAME, newName) }
        return db.update(TABLE_LISTS, values, "$COLUMN_LIST_ID=?", arrayOf(listId.toString()))
    }

    fun updateItem(itemId: Long, newText: String, checked: Boolean): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ITEM_TEXT, newText)
            put(COLUMN_ITEM_CHECKED, if (checked) 1 else 0)
        }
        return db.update(TABLE_ITEMS, values, "$COLUMN_ITEM_ID=?", arrayOf(itemId.toString()))
    }

    fun deleteItem(itemId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_ITEMS, "$COLUMN_ITEM_ID=?", arrayOf(itemId.toString()))
    }

    fun deleteItemsForList(listId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_ITEMS, "$COLUMN_ITEM_LIST_ID=?", arrayOf(listId.toString()))
    }

    fun deleteList(listId: Long): Int {
        val db = writableDatabase
        deleteItemsForList(listId)
        return db.delete(TABLE_LISTS, "$COLUMN_LIST_ID=?", arrayOf(listId.toString()))
    }

    fun getAllLists(): List<Pair<Long, String>> {
        val db = readableDatabase
        val cursor = db.query(TABLE_LISTS, arrayOf(COLUMN_LIST_ID, COLUMN_LIST_NAME),
            null, null, null, null, null)
        val lists = mutableListOf<Pair<Long, String>>()
        while (cursor.moveToNext()) {
            lists.add(Pair(cursor.getLong(0), cursor.getString(1)))
        }
        cursor.close()
        return lists
    }

    fun getItemsForList(listId: Long): List<Triple<Long, String, Boolean>> {
        val db = readableDatabase
        val cursor = db.query(TABLE_ITEMS, arrayOf(COLUMN_ITEM_ID, COLUMN_ITEM_TEXT, COLUMN_ITEM_CHECKED),
            "$COLUMN_ITEM_LIST_ID=?", arrayOf(listId.toString()), null, null, null)
        val items = mutableListOf<Triple<Long, String, Boolean>>()
        while (cursor.moveToNext()) {
            items.add(Triple(cursor.getLong(0), cursor.getString(1), cursor.getInt(2) == 1))
        }
        cursor.close()
        return items
    }
}
