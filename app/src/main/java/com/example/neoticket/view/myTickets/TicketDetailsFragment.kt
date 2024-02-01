package com.example.neoticket.view.myTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neoticket.databinding.FragmentTicketDetailsBinding

class TicketDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTicketDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

}