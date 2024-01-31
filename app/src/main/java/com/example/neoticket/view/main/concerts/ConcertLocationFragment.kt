package com.example.neoticket.view.main.concerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neoticket.databinding.FragmentConcertLocationBinding

class ConcertLocationFragment : Fragment() {
    private lateinit var binding: FragmentConcertLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConcertLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

}