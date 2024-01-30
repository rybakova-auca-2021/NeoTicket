package com.example.neoticket.view.main.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.databinding.FragmentSoonInCinemaBinding

class SoonInCinemaFragment : Fragment() {
    private lateinit var binding: FragmentSoonInCinemaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSoonInCinemaBinding.inflate(inflater, container, false)
        return binding.root
    }
}