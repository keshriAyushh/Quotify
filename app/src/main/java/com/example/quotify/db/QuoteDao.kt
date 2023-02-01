package com.example.quotify.db
import com.example.quotify.models.Result
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuote(quote: List<com.example.quotify.models.Result>)

    @Query("SELECT * FROM quotes")
    suspend fun getQuotes(): List<com.example.quotify.models.Result>

}