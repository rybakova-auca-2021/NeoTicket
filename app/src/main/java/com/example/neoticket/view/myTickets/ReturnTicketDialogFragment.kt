package com.example.neoticket.view.myTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentReturnTicketDialogBinding

class ReturnTicketDialogFragment : Fragment() {
    private lateinit var binding: FragmentReturnTicketDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReturnTicketDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

}