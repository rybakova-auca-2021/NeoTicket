package com.example.neoticket.view.main.cinema

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentChooseTicketTypeBinding

class ChooseTicketTypeFragment : Fragment() {
    private lateinit var binding: FragmentChooseTicketTypeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseTicketTypeBinding.inflate(inflater, container, false)
        return binding.root
    }


}