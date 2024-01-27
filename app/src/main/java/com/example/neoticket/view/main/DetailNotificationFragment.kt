package com.example.neoticket.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentDetailNotificationBinding

class DetailNotificationFragment : Fragment() {
    private lateinit var binding: FragmentDetailNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }
}