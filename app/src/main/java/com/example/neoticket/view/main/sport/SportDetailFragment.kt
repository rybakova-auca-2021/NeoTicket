package com.example.neoticket.view.main.sport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentSportDetailBinding

class SportDetailFragment : Fragment() {
    private lateinit var binding: FragmentSportDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}