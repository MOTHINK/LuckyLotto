package com.example.luckylotto.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.luckylotto.data.dao.PoolDao
import com.example.luckylotto.data.dao.TicketDao
import com.example.luckylotto.data.dao.WalletDao
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.model.Ticket
import com.example.luckylotto.data.model.Wallet

@Database(entities = [Pool::class, Ticket::class, Wallet::class], version = 2, exportSchema = false)
abstract class LuckyLottoDatabase : RoomDatabase() {

    abstract fun poolDao(): PoolDao
    abstract fun ticketDao(): TicketDao
    abstract fun walletDao(): WalletDao

    companion object {
        @Volatile
        private var Instance: LuckyLottoDatabase? = null
        private const val DB_NAME = "LuckyLottoDatabase"
        fun getDatabase(context: Context): LuckyLottoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LuckyLottoDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}