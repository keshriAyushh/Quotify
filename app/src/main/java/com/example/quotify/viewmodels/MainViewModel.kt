package com.example.quotify.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotify.models.QuoteList
import com.example.quotify.repository.QuoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: QuoteRepo,
    private var page: Int = 1
): ViewModel() {
    init{
        viewModelScope.launch(Dispatchers.IO) {
            repo.getQuotes(page)
        }
    }

    fun nextPage(){
        page++
        viewModelScope.launch {
            repo.getQuotes(page)
        }
    }
    fun prevPage() {
        page--
        viewModelScope.launch {
            repo.getQuotes(page)
        }
    }
    val quotes: LiveData<QuoteList>
        get() = repo.quotes
}