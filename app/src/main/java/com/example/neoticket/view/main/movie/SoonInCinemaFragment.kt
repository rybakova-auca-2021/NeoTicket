package com.example.neoticket.view.main.movie

import CinemaAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.FragmentSoonInCinemaBinding
import com.example.neoticket.viewModel.cinema.MovieAtBoxListViewModel
import com.example.neoticket.viewModel.cinema.MovieComingSoonListViewModel

class SoonInCinemaFragment : Fragment() {
    private lateinit var binding: FragmentSoonInCinemaBinding
    private lateinit var cinemaAdapter: CinemaAdapter
    private lateinit var recyclerViewCinema: RecyclerView
    private val movieViewModel: MovieComingSoonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSoonInCinemaBinding.inflate(inflater, container, false)
        recyclerViewCinema = binding.rvSoonInCinema
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        getCinemaList()
    }

    private fun setupAdapters() {
        cinemaAdapter = CinemaAdapter(emptyList())
        recyclerViewCinema.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerViewCinema.adapter = cinemaAdapter
    }

    private fun getCinemaList() {
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                cinemaAdapter.updateData(movies)
            }
        })
        movieViewModel.getMovies()
    }
}