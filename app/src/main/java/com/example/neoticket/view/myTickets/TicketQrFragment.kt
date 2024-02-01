package com.example.neoticket.view.myTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentTicketQrBinding

class TicketQrFragment : Fragment() {
    private lateinit var binding: FragmentTicketQrBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketQrBinding.inflate(inflater, container, false)
        return binding.root
    }

}