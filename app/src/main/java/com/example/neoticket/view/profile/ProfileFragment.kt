package com.example.neoticket.view.profile

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.neoticket.databinding.FragmentProfileBinding

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
        setupCard()
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

    private fun setupCard() {
        viewModel.getCard { cardList ->
            cardList.let {
                if (it.isNotEmpty()) {
                    val card = it[0]
                    binding.textCardMoney.text = card.balance
                    val lastFourDigits = card.account_number.takeLast(4)
                    binding.textCardNumber.text = "Платежная карта $lastFourDigits"
                } else {
                    binding.card.visibility = View.GONE
                }
            }
        }
    }



    private fun String?.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this ?: "")
    }
}