package com.example.ticder.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ticder.databinding.ItemImageBinding
import com.example.ticder.viewmodel.IImageViewModel

class ImagesAdapter(private val viewModel: IImageViewModel) : RecyclerView.Adapter<ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageHolder(binding, viewModel)
    }

    override fun getItemCount() = viewModel.getItemCount()

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind()
    }
}