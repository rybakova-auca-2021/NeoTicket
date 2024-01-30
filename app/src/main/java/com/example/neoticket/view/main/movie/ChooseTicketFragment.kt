package com.example.neoticket.view.main.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.neoticket.adapters.GridAdapterSeat
import com.example.neoticket.databinding.FragmentChooseTicketBinding

class ChooseTicketFragment : Fragment() {
   private lateinit var binding: FragmentChooseTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseTicketBinding.inflate(inflater, container, false)
        val gridView: GridView = binding.gridView
        val gridAdapterSeat = GridAdapterSeat(requireContext())
        gridView.adapter = gridAdapterSeat
        return binding.root
    }


}