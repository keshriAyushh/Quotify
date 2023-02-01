package com.example.quotify.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quotify.api.QuoteService
import com.example.quotify.db.QuoteDatabase
import com.example.quotify.models.QuoteList
import com.example.quotify.utils.NetworkUtils

class QuoteRepo(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val context: Context
) {
    private val quoteLiveData = MutableLiveData<QuoteList>()
    val quotes: LiveData<QuoteList> get() = quoteLiveData

    suspend fun getQuotes(page: Int){
        if(NetworkUtils.isNetworkAvailable(context)){
            val result = quoteService.getQuotes(page)
            if(result.body() !=null){
                quoteDatabase.quoteDao().addQuote(result.body()!!.results)
                quoteLiveData.postValue(result.body())
            }
        } else {
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
            quoteLiveData.postValue(quoteList)

        }
    }
}