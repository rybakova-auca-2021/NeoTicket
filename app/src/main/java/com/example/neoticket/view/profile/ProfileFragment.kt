package com.example.neoticket.view.profile

import android.media.tv.TvContract.Channels.Logo
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentProfileBinding
import com.example.neoticket.view.auth.CodeVerificationFragment

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupData()
    }

    private fun setupNavigation() {
        binding.btnLogout.setOnClickListener {
            val bottomSheetFragment = LogoutDialogFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
        listOf(binding.etLogin, binding.etEmail, binding.etPhone).forEach { editText ->
            editText.setOnClickListener {
                val bottomSheetFragment = EditProfileDialogFragment()
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
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
}