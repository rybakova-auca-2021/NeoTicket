package com.example.neoticket.view.main.cinema

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentConfirmTicketSeatsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfirmTicketSeatsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentConfirmTicketSeatsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmTicketSeatsBinding.inflate(inflater, container, false)
        return binding.root
    }
}