package com.example.quotify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotify.adapters.QuoteAdapter
import com.example.quotify.databinding.ActivityMainBinding
import com.example.quotify.models.Result
import com.example.quotify.repository.QuoteRepo
import com.example.quotify.viewmodels.MainViewModel
import com.example.quotify.viewmodels.MainViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), RecyclerViewClicksListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVM: MainViewModel
    private lateinit var repository: QuoteRepo
    private lateinit var quoteAdapter: QuoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        repository = (application as QuoteApplication).quoteRepo
        mainVM = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]


        mainVM.quotes.observe(this, Observer {
            val data = it.results
            data.toMutableList().shuffled()
            quoteAdapter = QuoteAdapter(
                this@MainActivity,
                data.toMutableList().shuffled(),
                listener = this
            )
            binding.recycler.adapter = quoteAdapter
            binding.recycler.layoutManager = LinearLayoutManager(this@MainActivity)
        })

        binding.btnNext.setOnClickListener {
            mainVM.nextPage()

        }

        binding.btnPrev.setOnClickListener {
            mainVM.prevPage()
        }

//        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy > 0) {
//                    binding.textView.visibility = View.GONE
//                } else {
//                    binding.textView.visibility = View.VISIBLE
//                }
//
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                    // @TODO
//                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    // @TODO
//                } else {
//                    // @TODO
//                }
//            }
//        })

    }

    override fun onItemClicked(view: View, quote: Result) {
        when (view.id) {

            R.id.btnShare -> {
                val btn = findViewById<FloatingActionButton>(view.id)

                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "${quote.content}\n-${quote.author}")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share Via"))

            }
        }
    }

}