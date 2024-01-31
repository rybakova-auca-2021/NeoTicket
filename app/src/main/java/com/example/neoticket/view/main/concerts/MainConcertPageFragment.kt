package com.example.neoticket.view.main.concerts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentMainConcertPageBinding

class MainConcertPageFragment : Fragment() {
    private lateinit var binding: FragmentMainConcertPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainConcertPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}