package com.example.youtube.ui.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.databinding.FragmentGeneralBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment() {
    private val listViewModel: ListViewModel by viewModel()
    private lateinit var binding: FragmentGeneralBinding
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralBinding.inflate(inflater, container, false).apply {
            viewModel = listViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel.fetchContentList()

        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        listViewModel.list.observe(viewLifecycleOwner, {
            adapter.update(it)
        })

        listViewModel.message.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setupAdapter() {
        if (binding.viewModel != null) {
            val layoutManager = LinearLayoutManager(activity)
            adapter = activity?.let { ListAdapter(it) }!!
            binding.contentsList.layoutManager = layoutManager
            binding.contentsList.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    layoutManager.orientation
                )
            )
            binding.contentsList.adapter = adapter
        }
    }
}