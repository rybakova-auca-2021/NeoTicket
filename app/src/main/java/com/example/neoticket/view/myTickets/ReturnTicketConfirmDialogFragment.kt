package com.example.neoticket.view.myTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.databinding.FragmentReturnTicketConfirmDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReturnTicketConfirmDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentReturnTicketConfirmDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReturnTicketConfirmDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
}