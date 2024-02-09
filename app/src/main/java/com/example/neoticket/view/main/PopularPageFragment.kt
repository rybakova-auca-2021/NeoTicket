package com.example.neoticket.view.main

import ConcertAdapter
import PopularAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentPopularPageBinding
import com.example.neoticket.model.Movie
import com.example.neoticket.model.Popular
import com.example.neoticket.viewModel.cinema.GetPopularListViewModel
import com.example.neoticket.viewModel.cinema.MovieComingSoonListViewModel


class PopularPageFragment : Fragment() {
    private lateinit var binding: FragmentPopularPageBinding
    private lateinit var adapter: PopularAdapter
    private lateinit var recyclerView: RecyclerView
    private val popularViewModel: GetPopularListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularPageBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        recyclerView = binding.rvPopular
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupNavigation()
        getCinemaList()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.mainPageFragment)
        }
    }
    private fun setupAdapters() {
        adapter = PopularAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun getCinemaList() {
        popularViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                adapter.updateData(result)
            }
        })
        popularViewModel.getPopularList()
    }
}