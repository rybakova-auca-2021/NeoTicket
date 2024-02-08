package com.example.neoticket.view.main.concerts

import ConcertAdapter
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
import com.example.neoticket.databinding.FragmentMainConcertPageBinding
import com.example.neoticket.model.Concert
import com.example.neoticket.viewModel.concerts.ConcertListViewModel

class MainConcertPageFragment : Fragment() {
    private lateinit var binding: FragmentMainConcertPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConcertAdapter
    private val viewModel: ConcertListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainConcertPageBinding.inflate(inflater, container, false)
        recyclerView = binding.rvConcerts
        (requireContext() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        getConcertList()
        setupNavigation()
    }

    private fun setupAdapters() {
        adapter = ConcertAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupNavigation() {
        adapter.setOnItemClickListener(object : ConcertAdapter.OnItemClickListener {
            override fun onItemClick(item: Concert) {
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                bundle.putString("sourceFragment", "mainConcertPage")
                findNavController().navigate(R.id.concertDetailPageFragment, bundle)
            }
        })

        binding.btnAllPlaces.setOnClickListener {
            val bottomSheetFragment = ConcertLocationFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_mainConcertPageFragment_to_mainPageFragment)
        }
    }

    private fun getConcertList() {
        viewModel.concertsLiveData.observe(viewLifecycleOwner, Observer { concerts ->
            if (concerts != null) {
                adapter.updateData(concerts)
            }
        })
        viewModel.getConcerts()
    }
}