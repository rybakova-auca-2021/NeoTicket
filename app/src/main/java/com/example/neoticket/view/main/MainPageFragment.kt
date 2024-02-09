package com.example.neoticket.view.main

import CinemaItem
import ConcertItem
import MainPageAdapter
import PopularItem
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
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentMainPageBinding
import com.example.neoticket.viewModel.cinema.GetPopularListViewModel
import com.example.neoticket.viewModel.cinema.MovieAtBoxListViewModel
import com.example.neoticket.viewModel.concerts.ConcertListViewModel
import com.example.neoticket.viewModel.theater.TheaterListViewModel

class MainPageFragment : Fragment() {
    private lateinit var binding: FragmentMainPageBinding
    private lateinit var cinemaAdapter: MainPageAdapter
    private lateinit var concertAdapter: MainPageAdapter
    private lateinit var theaterAdapter: MainPageAdapter
    private lateinit var popularAdapter: MainPageAdapter
    private lateinit var recyclerViewCinema: RecyclerView
    private lateinit var recyclerViewTheater: RecyclerView
    private lateinit var recyclerViewConcert: RecyclerView
    private lateinit var recyclerViewPopular: RecyclerView
    private val movieViewModel: MovieAtBoxListViewModel by viewModels()
    private val concertViewModel: ConcertListViewModel by viewModels()
    private val theaterViewModel: TheaterListViewModel by viewModels()
    private val popularViewModel: GetPopularListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).showBtmNav()
        recyclerViewCinema = binding.rvCinema
        recyclerViewConcert = binding.rvConcerts
        recyclerViewTheater = binding.rvTheater
        recyclerViewPopular = binding.rvPopular
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupAdapterClicks()
        setupNavigation()
        getPopularList()
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

        popularAdapter = MainPageAdapter(emptyList())
        recyclerViewPopular.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPopular.adapter = popularAdapter
    }

    private fun setupAdapterClicks() {
        cinemaAdapter.setOnCinemaClickListener(object : MainPageAdapter.OnCinemaClickListener {
            override fun onCinemaItemClick(item: CinemaItem) {
                val bundle = Bundle()
                bundle.putInt("id", item.cinema.id)
                bundle.putString("sourceFragment", "mainPageFragment")
                findNavController().navigate(R.id.detailMovieInCinemaFragment, bundle)
            }
        })
        concertAdapter.setOnConcertClickListener(object : MainPageAdapter.OnConcertClickListener {
            override fun onConcertItemClick(item: ConcertItem) {
                val bundle = Bundle()
                bundle.putInt("id", item.concert.id)
                bundle.putString("sourceFragment", "mainPageFragment")
                findNavController().navigate(R.id.concertDetailPageFragment, bundle)
            }
        })
        theaterAdapter.setOnTheaterClickListener(object : MainPageAdapter.OnTheaterClickListener {
            override fun onTheaterItemClick(item: TheaterItem) {
                val bundle = Bundle()
                bundle.putInt("id", item.theater.id)
                bundle.putString("sourceFragment", "mainPageFragment")
                findNavController().navigate(R.id.theaterDetailFragment, bundle)
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

    private fun getPopularList() {
        popularViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { popular ->
            val items = popular?.map { PopularItem(it) }
            if (items != null) {
                popularAdapter.updateData(items)
            }
        })
        popularViewModel.getPopularList()
    }

    private fun setupNavigation() {
        binding.btnLocation.setOnClickListener {
            val bottomSheetFragment = LocationFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        val cinemaAction = R.id.action_mainPageFragment_to_cinemaFragment
        val theaterAction = R.id.action_mainPageFragment_to_mainTheaterFragment
        val sportAction = R.id.action_mainPageFragment_to_mainSportFragment
        val concertAction = R.id.action_mainPageFragment_to_mainConcertPageFragment

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

        binding.btnConcert.setOnClickListener {
            findNavController().navigate(concertAction)
        }
        binding.btnAllConcert.setOnClickListener {
            findNavController().navigate(concertAction)
        }
        binding.btnAllPopular.setOnClickListener {
            findNavController().navigate(R.id.popularPageFragment)
        }
    }
}
