package com.example.quotify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotify.R
import com.example.quotify.RecyclerViewClicksListener
import com.google.android.material.floatingactionbutton.FloatingActionButton


class QuoteAdapter(
    private val context: Context,
    private val data: List<com.example.quotify.models.Result>,
    private val listener: RecyclerViewClicksListener
): RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.quote.text = data[position].content
        if(data[position].author.isNotEmpty()){
           holder.author!!.text = "-"+data[position].author
        } else {
            holder.author!!.text = "-Anonymous"
        }

        holder.share.setOnClickListener {
            listener.onItemClicked(it, data[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val quote = itemView.findViewById<TextView>(R.id.tvQuote)!!
        val author: TextView? = itemView.findViewById(R.id.tvAuthor)
        val share: FloatingActionButton = itemView.findViewById(R.id.btnShare)
    }
}