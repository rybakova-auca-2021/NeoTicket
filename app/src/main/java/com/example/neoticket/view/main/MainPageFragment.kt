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
import android.widget.SearchView
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

class MainPageFragment : Fragment(), OnCitySelectedListener {
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
        initializeViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupNavigation()
        fetchData()
        search()
    }

    private fun initializeViews() {
        recyclerViewCinema = binding.rvCinema
        recyclerViewConcert = binding.rvConcerts
        recyclerViewTheater = binding.rvTheater
        recyclerViewPopular = binding.rvPopular
    }

    private fun fetchData() {
        getPopularList()
        getCinemaList()
        getConcertList()
        getTheaterList()
    }

    private fun setupViews() {
        setupAdapters()
        setupAdapterClicks()
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
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner) { movies ->
            val cinemaItems = movies?.map { CinemaItem(it) }
            if (cinemaItems != null) {
                cinemaAdapter.updateData(cinemaItems)
            }
        }
        movieViewModel.getMovies()
    }


    private fun getTheaterList() {
        theaterViewModel.theaterLiveData.observe(viewLifecycleOwner) { movies ->
            val items = movies?.map { TheaterItem(it) }
            if (items != null) {
                theaterAdapter.updateData(items)
            }
        }
        theaterViewModel.getTheaters()
    }

    private fun getConcertList() {
        concertViewModel.concertsLiveData.observe(viewLifecycleOwner) { movies ->
            val concertItems = movies?.map { ConcertItem(it) }
            if (concertItems != null) {
                concertAdapter.updateData(concertItems)
            }
        }
        concertViewModel.getConcerts()
    }

    private fun getPopularList() {
        popularViewModel.moviesLiveData.observe(viewLifecycleOwner) { popular ->
            val items = popular?.map { PopularItem(it) }
            if (items != null) {
                popularAdapter.updateData(items)
            }
        }
        popularViewModel.getPopularList()
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { it ->
                    if (it.isNotEmpty()) {
                        initialSearchViews()
                        performSearch(it)
                    } else {
                       emptyResultViews()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { it ->
                    if (it.isNotEmpty()) {
                        initialSearchViews()
                        performSearch(it)
                    } else {
                        emptyResultViews()
                    }
                }
                return true
            }
        })
    }

    private fun performSearch(it: String) {
        movieViewModel.getMoviesBySearch(it) { moviesResult ->
            if (moviesResult.isEmpty()) {
                binding.searchError.visibility = View.VISIBLE
                binding.cardCinema.visibility = View.GONE
            } else {
                val cinemaItems = moviesResult.map { CinemaItem(it) }
                binding.searchError.visibility = View.GONE
                binding.cardCinema.visibility = View.VISIBLE
                cinemaAdapter.updateData(cinemaItems)
            }
        }
        theaterViewModel.getTheatersBySearch(it) { theatersResult ->
            if (theatersResult.isEmpty()) {
                binding.searchError.visibility = View.VISIBLE
                binding.cardTheater.visibility = View.GONE
            } else {
                val theaterItems = theatersResult.map { TheaterItem(it) }
                theaterAdapter.updateData(theaterItems)
                binding.searchError.visibility = View.GONE
                binding.cardTheater.visibility = View.VISIBLE
            }
        }
        concertViewModel.getConcertsBySearch(it) { concertsResult ->
            if (concertsResult.isEmpty()) {
                binding.searchError.visibility = View.VISIBLE
                binding.cardConcert.visibility = View.GONE
            } else {
                val concertItems = concertsResult.map { ConcertItem(it) }
                concertAdapter.updateData(concertItems)
                binding.searchError.visibility = View.GONE
                binding.cardConcert.visibility = View.VISIBLE
            }
        }
    }

    private fun initialSearchViews() {
        binding.searchError.visibility = View.GONE
        binding.cardPopular.visibility = View.GONE
        binding.cardView3.visibility = View.GONE
        binding.searchError.visibility = View.GONE
        binding.cardConcert.visibility = View.VISIBLE
        binding.cardCinema.visibility = View.VISIBLE
        binding.cardTheater.visibility = View.VISIBLE
    }
    private fun emptyResultViews() {
        binding.searchError.visibility = View.VISIBLE
        binding.cardConcert.visibility = View.GONE
        binding.cardCinema.visibility = View.GONE
        binding.cardTheater.visibility = View.GONE
    }

    private fun setupNavigation() {
        setupLocationButton()
        setupNavigationButtons()
    }

    private fun setupLocationButton() {
        binding.btnLocation.setOnClickListener {
            val bottomSheetFragment = LocationFragment()
            bottomSheetFragment.setOnLocationSelectedListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun setupNavigationButtons() {
        val cinemaAction = R.id.action_mainPageFragment_to_cinemaFragment
        val theaterAction = R.id.action_mainPageFragment_to_mainTheaterFragment
        val sportAction = R.id.action_mainPageFragment_to_mainSportFragment
        val concertAction = R.id.action_mainPageFragment_to_mainConcertPageFragment

        binding.apply {
            btnAllCinema.setOnClickListener { findNavController().navigate(cinemaAction) }
            btnCinema.setOnClickListener { findNavController().navigate(cinemaAction) }

            btnAllTheater.setOnClickListener { findNavController().navigate(theaterAction) }
            btnTheater.setOnClickListener { findNavController().navigate(theaterAction) }

            btnSport.setOnClickListener { findNavController().navigate(sportAction) }

            btnConcert.setOnClickListener { findNavController().navigate(concertAction) }
            btnAllConcert.setOnClickListener { findNavController().navigate(concertAction) }
            btnAllPopular.setOnClickListener { findNavController().navigate(R.id.popularPageFragment) }
            btnNotifications.setOnClickListener { findNavController().navigate(R.id.notificationsFragment) }
        }
    }

    override fun onCitySelected(location: String) {
        updateMoviesByLocation(location)
        updateTheatersByLocation(location)
        updateConcertsByLocation(location)
        updatePopularListByLocation(location)
    }

    private fun updateMoviesByLocation(location: String) {
        movieViewModel.getMoviesByLocation(location) { result ->
            val items = result.map { CinemaItem(it) }
            cinemaAdapter.updateData(items)
        }
    }

    private fun updateTheatersByLocation(location: String) {
        theaterViewModel.getTheatersByLocation(location) { result ->
            val items = result.map { TheaterItem(it) }
            theaterAdapter.updateData(items)
        }
    }

    private fun updateConcertsByLocation(location: String) {
        concertViewModel.getConcertsByLocation(location) { result ->
            val items = result.map { ConcertItem(it) }
            concertAdapter.updateData(items)
        }
    }

    private fun updatePopularListByLocation(location: String) {
        popularViewModel.getPopularListByLocation(location) { result ->
            val items = result.map { PopularItem(it) }
            popularAdapter.updateData(items)
        }
    }

}
