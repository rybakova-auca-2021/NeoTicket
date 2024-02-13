package com.example.neoticket.view.auth

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.neoticket.databinding.FragmentAdditionalInfoBinding
import com.example.neoticket.view.profile.ProfileDataViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AdditionalInfoFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAdditionalInfoBinding
    private val viewModel: ProfileDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = arguments?.getString("email")

        if (email != null) {
            binding.btnReady.setOnClickListener {
                editData(email)
            }
        }
    }

    private fun setupData() {
        viewModel.getProfile { profileData ->
            profileData?.let {
                binding.etLogin.text = profileData.username?.toEditable()
                binding.etPhone.text = profileData.phone?.toEditable()
            }
        }
    }

    private fun String?.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this ?: "")
    }

    private fun editData(email: String) {
        val username = binding.etLogin.text.toString()
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