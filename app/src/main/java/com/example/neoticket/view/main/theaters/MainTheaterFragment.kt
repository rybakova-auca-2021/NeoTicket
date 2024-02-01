package com.example.neoticket.view.main.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neoticket.databinding.FragmentMainTheaterBinding

class MainTheaterFragment : Fragment() {
    private lateinit var binding: FragmentMainTheaterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainTheaterBinding.inflate(inflater, container, false)
        return binding.root
    }

}