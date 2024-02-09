package com.example.neoticket.view.myTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentMyTicketsBinding


class MyTicketsFragment : Fragment() {
    private lateinit var binding: FragmentMyTicketsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyTicketsBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).showBtmNav()
        return binding.root
    }
}