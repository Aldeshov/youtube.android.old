package com.example.youtube.ui.video_content_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.databinding.VideoContentListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment: Fragment() {
    private val listViewModel: ListViewModel by viewModel()
    private lateinit var binding: VideoContentListBinding
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VideoContentListBinding.inflate(inflater, container, false).apply {
            viewModel = listViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.fetchContentList()

        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        binding.viewModel?.list?.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        binding.viewModel?.message?.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setupAdapter() {
        if (binding.viewModel != null) {
            val layoutManager = LinearLayoutManager(activity)
            adapter = activity?.let { ListAdapter(it) }!!
            binding.contentsList.layoutManager = layoutManager
            binding.contentsList.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
            binding.contentsList.adapter = adapter
        }
    }
}