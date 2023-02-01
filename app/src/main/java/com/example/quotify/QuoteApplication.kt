package com.example.quotify

import android.app.Application
import com.example.quotify.api.QuoteService
import com.example.quotify.api.RetrofitHelper
import com.example.quotify.db.QuoteDatabase
import com.example.quotify.repository.QuoteRepo

class QuoteApplication: Application() {

    lateinit var quoteRepo: QuoteRepo

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val quoteDatabase = QuoteDatabase.getDatabase(applicationContext)
        quoteRepo = QuoteRepo(quoteService, quoteDatabase, applicationContext)
    }
}