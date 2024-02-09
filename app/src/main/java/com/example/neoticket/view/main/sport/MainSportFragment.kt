package com.example.neoticket.view.main.sport

import SportsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentMainSportBinding
import com.example.neoticket.model.SportData
import com.example.neoticket.model.Theater
import com.example.neoticket.view.main.theaters.TheaterLocationFragment
import com.example.neoticket.viewModel.sport.SportListViewModel
import com.example.neoticket.viewModel.theater.TheaterListViewModel

class MainSportFragment : Fragment() {
    private lateinit var binding: FragmentMainSportBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SportsAdapter
    private val viewModel: SportListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainSportBinding.inflate(inflater, container, false)
        recyclerView = binding.rvSports
        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        getSportList()
        setupNavigation()
        search()
    }

    private fun setupAdapters() {
        adapter = SportsAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupNavigation() {
        adapter.setOnItemClickListener(object : SportsAdapter.OnItemClickListener {
            override fun onItemClick(item: SportData) {
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                bundle.putString("sourceFragment", "mainSportPage")
                findNavController().navigate(R.id.sportDetailFragment, bundle)
            }
        })

        binding.btnAllPlaces.setOnClickListener {
            val bottomSheetFragment = SportLocationsFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_mainSportFragment_to_mainPageFragment)
        }
    }

    private fun getSportList() {
        viewModel.sportLiveData.observe(viewLifecycleOwner, Observer { sports ->
            if (sports != null) {
                adapter.updateData(sports)
            }
        })
        viewModel.getSportList()
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) {
                        viewModel.getSportListBySearch(it) { result ->
                            adapter.updateData(result)
                        }
                    }
                }
                return true
            }
        })
    }


}