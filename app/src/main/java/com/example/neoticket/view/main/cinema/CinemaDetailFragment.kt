package com.example.neoticket.view.main.cinema

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentCinemaDetailBinding

class CinemaDetailFragment : Fragment() {
    private lateinit var binding: FragmentCinemaDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinemaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}