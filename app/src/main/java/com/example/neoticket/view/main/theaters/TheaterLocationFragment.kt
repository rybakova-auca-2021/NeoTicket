package com.example.neoticket.view.main.theaters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentTheaterLocationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TheaterLocationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTheaterLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheaterLocationBinding.inflate(inflater, container, false)
        return binding.root
    }
}