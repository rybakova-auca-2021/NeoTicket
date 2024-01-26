package com.example.neoticket.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.Utils.Util
import com.example.neoticket.databinding.FragmentLogoutDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogoutDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLogoutDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogoutDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnLogout.setOnClickListener {
            Util.token = null
            dismiss()
        }
    }

}