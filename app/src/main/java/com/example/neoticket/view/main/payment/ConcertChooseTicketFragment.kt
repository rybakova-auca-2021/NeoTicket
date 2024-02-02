package com.example.neoticket.view.main.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.neoticket.R
import com.example.neoticket.adapters.GridAdapterSeat
import com.example.neoticket.databinding.FragmentConcertChooseTicketBinding

class ConcertChooseTicketFragment : Fragment() {
    private lateinit var binding: FragmentConcertChooseTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConcertChooseTicketBinding.inflate(inflater, container, false)
        val gridView: GridView = binding.gridView
        val gridAdapterSeat = GridAdapterSeat(requireContext())
        gridView.adapter = gridAdapterSeat
        return binding.root
    }
}