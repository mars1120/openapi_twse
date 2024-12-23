package com.stock.twse.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stock.twse.StockDayAllItem
import com.stock.twse.db.dao.StockDayAllItemDao

@Database(entities = [StockDayAllItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stockDayAllItemDao(): StockDayAllItemDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        private const val DATABASE_NAME = "good_news.db"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                    .allowMainThreadQueries()
                    .build().also { instance = it }
            }
        }
    }
}