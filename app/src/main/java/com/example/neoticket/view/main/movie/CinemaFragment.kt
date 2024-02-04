package com.example.neoticket.view.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.adapters.ViewPagerAdapter
import com.example.neoticket.databinding.FragmentCinemaBinding
import com.example.neoticket.view.main.LocationFragment
import com.example.neoticket.view.main.movie.cinema.CinemaListFragment
import com.google.android.material.tabs.TabLayoutMediator

class CinemaFragment : Fragment() {
    private lateinit var binding: FragmentCinemaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinemaBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.fragmentHolder

        viewPager2.adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "В прокате"
                1 -> tab.text = "Скоро в кино"
            }
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_cinemaFragment_to_mainPageFragment)
        }
        binding.btnCinemas.setOnClickListener {
            val bottomSheetFragment = CinemaListFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }
}