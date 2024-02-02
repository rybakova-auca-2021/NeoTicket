package com.example.neoticket.view.main.movie.cinema

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentCinemaBinding
import com.example.neoticket.databinding.FragmentCinemaListBinding


class CinemaListFragment : Fragment() {
    private lateinit var binding: FragmentCinemaListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinemaListBinding.inflate(inflater, container, false)
        return binding.root
    }
}