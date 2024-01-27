package com.example.neoticket.view.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentAppInfoBinding

class AppInfoFragment : Fragment() {
    private lateinit var binding: FragmentAppInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_appInfoFragment_to_infoPageFragment)
        }
    }
}