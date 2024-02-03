package com.example.neoticket.view.main.movie

import MoviesAdapter
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
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentSoonInCinemaBinding
import com.example.neoticket.model.Movie
import com.example.neoticket.viewModel.cinema.MovieComingSoonListViewModel

class SoonInCinemaFragment : Fragment() {
    private lateinit var binding: FragmentSoonInCinemaBinding
    private lateinit var moviesAdapter: MoviesAdapter
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
        setupNavigation()
    }

    private fun setupAdapters() {
        moviesAdapter = MoviesAdapter(emptyList())
        recyclerViewCinema.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerViewCinema.adapter = moviesAdapter
    }

    private fun getCinemaList() {
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                moviesAdapter.updateData(movies)
            }
        })
        movieViewModel.getMovies()
    }

    private fun setupNavigation() {
        moviesAdapter.setOnItemClickListener(object : MoviesAdapter.OnItemClickListener {
            override fun onCinemaItemClick(item: Movie) {
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                bundle.putString("sourceFragment", "soonInCinema")
                findNavController().navigate(R.id.detailMovieFragment, bundle)
            }
        })
    }
}