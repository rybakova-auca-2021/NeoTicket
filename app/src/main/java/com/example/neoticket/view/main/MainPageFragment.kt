package com.example.neoticket.view.main

import CinemaAdapter
import CinemaItem
import ConcertItem
import MainPageAdapter
import TheaterItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentMainPageBinding
import com.example.neoticket.model.Cinema
import com.example.neoticket.model.Movie
import com.example.neoticket.viewModel.cinema.MovieAtBoxListViewModel
import com.example.neoticket.viewModel.concerts.ConcertListViewModel
import com.example.neoticket.viewModel.theater.TheaterListViewModel

class MainPageFragment : Fragment() {
    private lateinit var binding: FragmentMainPageBinding
    private lateinit var cinemaAdapter: MainPageAdapter
    private lateinit var concertAdapter: MainPageAdapter
    private lateinit var theaterAdapter: MainPageAdapter
    private lateinit var recyclerViewCinema: RecyclerView
    private lateinit var recyclerViewTheater: RecyclerView
    private lateinit var recyclerViewConcert: RecyclerView
    private val movieViewModel: MovieAtBoxListViewModel by viewModels()
    private val concertViewModel: ConcertListViewModel by viewModels()
    private val theaterViewModel: TheaterListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        recyclerViewCinema = binding.rvCinema
        recyclerViewConcert = binding.rvConcerts
        recyclerViewTheater = binding.rvTheater
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupAdapterClicks()
        setupNavigation()
        getCinemaList()
        getConcertList()
        getTheaterList()
    }

    private fun setupAdapters() {
        cinemaAdapter = MainPageAdapter(emptyList())
        recyclerViewCinema.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCinema.adapter = cinemaAdapter

        concertAdapter = MainPageAdapter(emptyList())
        recyclerViewConcert.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewConcert.adapter = concertAdapter

        theaterAdapter = MainPageAdapter(emptyList())
        recyclerViewTheater.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTheater.adapter = theaterAdapter
    }

    private fun setupAdapterClicks() {
        cinemaAdapter.setOnItemClickListener(object : MainPageAdapter.OnItemClickListener {
            override fun onCinemaItemClick(item: CinemaItem) {
                val bundle = Bundle()
                bundle.putInt("id", item.cinema.id)
                bundle.putString("sourceFragment", "mainPageFragment")
                findNavController().navigate(R.id.detailMovieInCinemaFragment, bundle)
            }

            override fun onConcertItemClick(item: ConcertItem) {
                TODO("Not yet implemented")
            }

            override fun onTheaterItemClick(item: TheaterItem) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getCinemaList() {
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { movies ->
            val cinemaItems = movies?.map { CinemaItem(it) }
            if (cinemaItems != null) {
                cinemaAdapter.updateData(cinemaItems)
            }
        })
        movieViewModel.getMovies()
    }


    private fun getTheaterList() {
        theaterViewModel.theaterLiveData.observe(viewLifecycleOwner, Observer { movies ->
            val items = movies?.map { TheaterItem(it) }
            if (items != null) {
                theaterAdapter.updateData(items)
            }
        })
        theaterViewModel.getTheaters()
    }

    private fun getConcertList() {
        concertViewModel.concertsLiveData.observe(viewLifecycleOwner, Observer { movies ->
            val concertItems = movies?.map { ConcertItem(it) }
            if (concertItems != null) {
                concertAdapter.updateData(concertItems)
            }
        })
        concertViewModel.getConcerts()
    }

    private fun setupNavigation() {
        binding.btnLocation.setOnClickListener {
            val bottomSheetFragment = LocationFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        val cinemaAction = R.id.action_mainPageFragment_to_cinemaFragment
        val theaterAction = R.id.action_mainPageFragment_to_mainTheaterFragment
        val sportAction = R.id.action_mainPageFragment_to_mainSportFragment

        binding.btnAllCinema.setOnClickListener {
            findNavController().navigate(cinemaAction)
        }
        binding.btnCinema.setOnClickListener {
            findNavController().navigate(cinemaAction)
        }

        binding.btnAllTheater.setOnClickListener {
            findNavController().navigate(theaterAction)
        }
        binding.btnTheater.setOnClickListener {
            findNavController().navigate(theaterAction)
        }

        binding.btnSport.setOnClickListener {
            findNavController().navigate(sportAction)
        }
    }
}
