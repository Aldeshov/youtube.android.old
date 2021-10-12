package com.example.youtube.ui.video_content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youtube.R
import com.example.youtube.service.models.VideoContent

class ContentFragment(private var item: VideoContent): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.video_content_item, container, false)
        return layout
    }
}