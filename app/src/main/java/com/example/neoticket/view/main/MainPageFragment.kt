package com.example.neoticket.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentMainPageBinding
import com.example.neoticket.view.profile.LogoutDialogFragment

class MainPageFragment : Fragment() {
    private lateinit var binding: FragmentMainPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnLocation.setOnClickListener {
            val bottomSheetFragment = LocationFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
        binding.btnAllCinema.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_cinemaFragment)
        }
        binding.btnCinema.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_cinemaFragment)
        }
    }
}