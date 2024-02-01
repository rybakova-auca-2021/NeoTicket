package com.example.neoticket.view.main.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neoticket.databinding.FragmentTheaterDescriptionBinding


class TheaterDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentTheaterDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheaterDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

}