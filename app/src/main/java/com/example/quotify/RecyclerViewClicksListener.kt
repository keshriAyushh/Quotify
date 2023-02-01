package com.example.quotify

import android.view.View

interface RecyclerViewClicksListener {

    fun onItemClicked(view: View, quote: com.example.quotify.models.Result)
}