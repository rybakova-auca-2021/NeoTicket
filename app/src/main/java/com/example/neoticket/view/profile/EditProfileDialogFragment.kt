package com.example.neoticket.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentEditProfileDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditProfileDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditProfileDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

}