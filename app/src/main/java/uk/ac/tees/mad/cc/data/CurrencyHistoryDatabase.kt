package uk.ac.tees.mad.cc.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyHistory::class], version = 1, exportSchema = false)
abstract class CurrencyHistoryDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}