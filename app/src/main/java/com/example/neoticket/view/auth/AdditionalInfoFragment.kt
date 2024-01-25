package com.example.neoticket.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentAdditionalInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AdditionalInfoFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAdditionalInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

}