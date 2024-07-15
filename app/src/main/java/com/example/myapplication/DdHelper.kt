package com.example.adddatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.Players

class DdHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "players_db", factory, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE players (id INTEGER PRIMARY KEY AUTOINCREMENT, name1 TEXT, name2 TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS players")
        onCreate(db)
    }

    fun addNames(player: Players){
        val values = ContentValues()
        values.put("name1", player.name1)
        values.put("name2", player.name2)

        val db = this.writableDatabase
        db.insert("players", null, values)
        db.close()
    }

    fun getPlayer(): Players? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM players ORDER BY id DESC LIMIT 1", null)

        var player: Players? = null

        if (cursor.moveToFirst()) {
            val name1 = cursor.getString(cursor.getColumnIndexOrThrow("name1"))
            val name2 = cursor.getString(cursor.getColumnIndexOrThrow("name2"))
            player = Players(name1, name2)
        }

        cursor.close()
        db.close()

        return player
    }
}
