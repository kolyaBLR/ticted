package com.example.ticder.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.ticder.databinding.ItemImageBinding
import com.example.ticder.viewmodel.IImageViewModel

class ImageHolder(
    private val binding: ItemImageBinding,
    private val viewModel: IImageViewModel
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind() {
        binding.image.bitmap = null
        val owner = context as LifecycleOwner
        viewModel.getImageLiveData(adapterPosition).observe(owner, Observer { items ->
            items[adapterPosition]?.let { bitmap ->
                binding.image.bitmap = bitmap
            }
        })
        viewModel.decodeBitmapIfNeed(context, adapterPosition)
    }
}