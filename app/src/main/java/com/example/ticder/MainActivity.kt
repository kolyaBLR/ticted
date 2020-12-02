package com.example.ticder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticder.databinding.ActivityMainBinding
import com.example.ticder.ui.ImagesAdapter
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

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ImagesAdapter(viewModel)
    }
}