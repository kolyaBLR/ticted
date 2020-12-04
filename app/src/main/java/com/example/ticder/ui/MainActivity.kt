package com.example.ticder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.ticder.TicderApplication
import com.example.ticder.databinding.ActivityMainBinding
import com.example.ticder.viewmodel.IImageViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @set:Inject
    lateinit var viewModel: IImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TicderApplication.instance.create(this).inject(this)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ImagesAdapter(viewModel)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        PagerSnapHelper().attachToRecyclerView(binding.recyclerView)
        val swipeHelper = SwipeHelper(viewModel)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.addOnScrollListener(AnimationScrollListener())

        viewModel.getSwipeLiveData().observe(this, Observer { swipeInfo ->
            adapter.notifyItemRemoved(swipeInfo.position)
        })
        viewModel.getCompletedLiveData().observe(this, Observer { likedImages ->
            swipeHelper.swipeEnabled = false
            adapter.notifyItemRangeInserted(0, likedImages.size)
        })
    }
}