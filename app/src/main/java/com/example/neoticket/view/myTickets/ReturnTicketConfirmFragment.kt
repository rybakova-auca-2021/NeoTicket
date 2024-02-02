package com.example.neoticket.view.myTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.databinding.FragmentReturnTicketConfirmBinding

class ReturnTicketConfirmFragment : Fragment() {
    private lateinit var binding: FragmentReturnTicketConfirmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReturnTicketConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

}