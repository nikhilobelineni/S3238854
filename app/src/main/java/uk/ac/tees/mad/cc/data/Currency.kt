package uk.ac.tees.mad.cc.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_history")
data class CurrencyHistory(
    @PrimaryKey(autoGenerate = true)val id : Int = 0,
    val fromCurrency: String,
    val toCurrency: String,
    val amount: Double,
    val result: Double,
    val date: String
)
