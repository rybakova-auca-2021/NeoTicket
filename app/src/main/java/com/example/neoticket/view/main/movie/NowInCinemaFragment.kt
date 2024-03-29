package com.example.neoticket.view.main.movie

import MoviesAdapter
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
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentNowInCinemaBinding
import com.example.neoticket.model.Movie
import com.example.neoticket.view.main.movie.cinema.CinemaListFragment
import com.example.neoticket.viewModel.cinema.MovieAtBoxListViewModel

class NowInCinemaFragment : Fragment() {
    private lateinit var binding: FragmentNowInCinemaBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerViewCinema: RecyclerView
    private val movieViewModel: MovieAtBoxListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowInCinemaBinding.inflate(inflater, container, false)
        recyclerViewCinema = binding.rvNowInCinema
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        getCinemaList()
        setupNavigation()
        search()
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
                bundle.putString("sourceFragment", "nowInCinema")
                findNavController().navigate(R.id.detailMovieInCinemaFragment, bundle)
            }
        })

        binding.btnCinemas.setOnClickListener {
            val bottomSheetFragment = CinemaListFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) {
                        movieViewModel.getMoviesBySearch(it) { result ->
                            moviesAdapter.updateData(result)
                        }
                    } else {
                        getCinemaList()
                    }
                }
                return true
            }
        })
    }
}