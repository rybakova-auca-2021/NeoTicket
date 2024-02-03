package com.example.neoticket.view.main.theaters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentTheaterDetailBinding

class TheaterDetailFragment : Fragment() {
    private lateinit var binding: FragmentTheaterDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheaterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}