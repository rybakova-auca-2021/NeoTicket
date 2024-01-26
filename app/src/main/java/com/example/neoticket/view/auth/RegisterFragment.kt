package com.example.neoticket.view.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentRegisterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupValidation()
    }

    private fun setupNavigation() {
        binding.btnGetCode.setOnClickListener {
            register()
        }
    }

    private fun setupValidation() {
        val textWatchers = arrayOf(
            binding.etEmail,
        )
        for (watchedText in textWatchers) {
            watchedText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                @SuppressLint("ResourceAsColor")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val email = binding.etEmail.text.toString()
                    binding.btnGetCode.isEnabled = email.isNotEmpty()

                    val whiteColor = resources.getColor(R.color.white)
                    binding.btnGetCode.setTextColor(whiteColor)
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    private fun register() {
        //TODO
    }
}