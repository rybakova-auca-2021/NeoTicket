package com.example.neoticket.view.main.theaters

import TheatersAdapter
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
import com.example.neoticket.databinding.FragmentMainTheaterBinding
import com.example.neoticket.model.Theater
import com.example.neoticket.viewModel.theater.TheaterListViewModel

class MainTheaterFragment : Fragment(), OnTheaterLocationSelectedListener {
    private lateinit var binding: FragmentMainTheaterBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TheatersAdapter
    private val viewModel: TheaterListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainTheaterBinding.inflate(inflater, container, false)
        recyclerView = binding.rvTheater
        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        getTheaterList()
        setupNavigation()
        search()
    }

    private fun setupAdapters() {
        adapter = TheatersAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupNavigation() {
        adapter.setOnItemClickListener(object : TheatersAdapter.OnItemClickListener {
            override fun onCinemaItemClick(item: Theater) {
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                bundle.putString("sourceFragment", "mainTheaterPage")
                findNavController().navigate(R.id.theaterDetailFragment, bundle)
            }
        })

        binding.btnAllPlaces.setOnClickListener {
            val bottomSheetFragment = TheaterLocationFragment()
            bottomSheetFragment.setOnLocationSelectedListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_mainTheaterFragment_to_mainPageFragment)
        }
    }

    private fun getTheaterList() {
        viewModel.theaterLiveData.observe(viewLifecycleOwner, Observer { theaters ->
            if (theaters != null) {
                adapter.updateData(theaters)
            }
        })
        viewModel.getTheaters()
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) {
                        viewModel.getTheatersBySearch(it) { result ->
                            adapter.updateData(result)
                        }
                    }
                }
                return true
            }
        })
    }

    override fun onLocationSelected(location: String) {
        println("onLocationSelected $location")
        viewModel.getTheatersByPlace(location)
        viewModel.theaterLiveData.observe(viewLifecycleOwner, Observer { theaters ->
            if (theaters != null) {
                adapter.updateData(theaters)
            }
        })
    }
}