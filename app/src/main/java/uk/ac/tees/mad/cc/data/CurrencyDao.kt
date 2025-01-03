package uk.ac.tees.mad.cc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency_history")
    suspend fun getAllHistory(): List<CurrencyHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: CurrencyHistory)

    @Query("DELETE FROM currency_history")
    suspend fun deleteHistory()

    @Delete
    suspend fun delete(history: CurrencyHistory)

}