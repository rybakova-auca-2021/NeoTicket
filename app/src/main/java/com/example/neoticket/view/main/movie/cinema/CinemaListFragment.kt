package com.example.neoticket.view.main.movie.cinema

import CinemaAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentCinemaListBinding
import com.example.neoticket.model.Cinema
import com.example.neoticket.model.Movie
import com.example.neoticket.viewModel.cinema.CinemaListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CinemaListFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCinemaListBinding
    private val viewModel: CinemaListViewModel by viewModels()
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter: CinemaAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinemaListBinding.inflate(inflater, container, false)
        recyclerView = binding.rvCinemas
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
        adapter = CinemaAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun getCinemaList() {
        viewModel.cinemasLiveData.observe(viewLifecycleOwner, Observer { cinemas ->
            if (cinemas != null) {
                adapter.updateData(cinemas)
            }
        })
        viewModel.getCinemas()
    }

    private fun setupNavigation() {
        adapter.setOnItemClickListener(object : CinemaAdapter.OnItemClickListener {
            override fun onCinemaItemClick(item: Cinema) {
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                findNavController().navigate(R.id.cinemaDetailFragment2, bundle)
            }
        })
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) {
                        viewModel.getCinemasBySearch(it) { result ->
                            if (result.isEmpty()) {
                                binding.imgError.visibility = View.VISIBLE
                                binding.rvCinemas.visibility = View.GONE
                            } else {
                                binding.imgError.visibility = View.GONE
                                binding.rvCinemas.visibility = View.VISIBLE
                                adapter.updateData(result)
                            }
                        }
                    } else {
                        binding.imgError.visibility = View.VISIBLE
                        binding.rvCinemas.visibility = View.GONE
                    }
                }
                return true
            }
        })
    }
}