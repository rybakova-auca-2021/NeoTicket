package com.example.neoticket.view.main.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentTimeIsUpDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimeIsUpDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTimeIsUpDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeIsUpDialogBinding.inflate(inflater, container, false)
        return binding.root
    }


}