package com.example.recyclerview.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview.data.model.MarsPhoto
import com.example.recyclerview.databinding.ItemMarsPhotoBinding

class MarsPhotoAdapter(
    private val onClick: (MarsPhoto) -> Unit
) : RecyclerView.Adapter<MarsPhotoViewHolder>() {

    private var list: List<MarsPhoto> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(data: List<MarsPhoto>) {
        this.list = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        val binding = ItemMarsPhotoBinding.inflate(LayoutInflater.from(parent.context))
        return MarsPhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding){
            rover.text = "Rover: ${item.rover.name}"
            camera.text = "Camera: ${item.camera.name}"
            sol.text = "Sol: ${item.sol}"
            date.text = "Date: ${item.earthDate}"
            item.let {
                Glide
                    .with(image.context)
                    .load(it.imgSrc)
                    .centerCrop()
                    .into(image)
            }
            root.setOnClickListener {
                onClick(item)
            }
        }

    }
}

class MarsPhotoViewHolder(val binding: ItemMarsPhotoBinding) :
    RecyclerView.ViewHolder(binding.root)