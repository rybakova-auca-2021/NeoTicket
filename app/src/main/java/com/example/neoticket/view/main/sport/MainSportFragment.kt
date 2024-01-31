package com.example.neoticket.view.main.sport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentMainSportBinding

class MainSportFragment : Fragment() {
    private lateinit var binding: FragmentMainSportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainSportBinding.inflate(inflater, container, false)
        return binding.root
    }


}