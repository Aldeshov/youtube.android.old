package com.example.youtube.ui.general

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.databinding.VideoContentItemBinding
import com.example.youtube.service.models.api.content.VideoContent

class ListAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private var list: ArrayList<VideoContent> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = VideoContentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(dataBinding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(list: ArrayList<VideoContent>) {
        this.list = list
        notifyDataSetChanged() // TODO check this function
    }
}