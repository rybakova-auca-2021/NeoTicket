package com.example.neoticket.view.main.sport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neoticket.databinding.FragmentSportLocationsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SportLocationsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSportLocationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }
}