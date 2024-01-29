package com.example.neoticket.view.main.cinema

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentNowInCinemaBinding

class NowInCinemaFragment : Fragment() {
    private lateinit var binding: FragmentNowInCinemaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowInCinemaBinding.inflate(inflater, container, false)
        return binding.root
    }
}