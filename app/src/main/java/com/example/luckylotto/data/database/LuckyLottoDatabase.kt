package com.example.luckylotto.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.luckylotto.data.dao.PoolDao
import com.example.luckylotto.data.model.Pool

@Database(entities = [Pool::class], version = 1, exportSchema = false)
abstract class LuckyLottoDatabase : RoomDatabase() {

    abstract fun poolDao(): PoolDao

    companion object {
        @Volatile
        private var Instance: LuckyLottoDatabase? = null
        fun getDatabase(context: Context): LuckyLottoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LuckyLottoDatabase::class.java, "LuckyLottoDatabase")
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }

}