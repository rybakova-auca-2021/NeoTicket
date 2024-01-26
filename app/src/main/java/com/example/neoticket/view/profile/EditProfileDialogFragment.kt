package com.example.neoticket.view.profile

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentEditProfileDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditProfileDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditProfileDialogBinding
    private val viewModel: ProfileDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupData()
    }

    private fun setupNavigation() {
        binding.btnReady.setOnClickListener {
            editData()
        }
    }

    private fun setupData() {
        viewModel.getProfile { profileData ->
            profileData?.let {
                binding.etLogin.text = profileData.username?.toEditable()
                binding.etPhone.text = profileData.phone?.toEditable()
                binding.etEmail.text = profileData.email?.toEditable()
            }
        }
    }

    private fun String?.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this ?: "")
    }

    private fun editData() {
        val username = binding.etLogin.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()

        viewModel.updateProfile(
            username,
            email,
            phone,
            onSuccess = {
                dismiss()
            }
        )
    }

}